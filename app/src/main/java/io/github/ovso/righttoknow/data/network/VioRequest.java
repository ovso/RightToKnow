package io.github.ovso.righttoknow.data.network;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import io.github.ovso.righttoknow.data.network.model.VioData;
import io.github.ovso.righttoknow.data.network.model.certified.Certified;
import io.github.ovso.righttoknow.data.network.model.certified.CertifiedData;
import io.github.ovso.righttoknow.data.network.model.violation.Violation;
import io.github.ovso.righttoknow.data.network.model.violation.ViolationContents;
import io.github.ovso.righttoknow.data.network.model.violation.ViolationData;
import io.github.ovso.righttoknow.data.network.model.violators.Violator;
import io.github.ovso.righttoknow.data.network.model.violators.ViolatorContents;
import io.github.ovso.righttoknow.data.network.model.violators.ViolatorData;
import io.github.ovso.righttoknow.framework.utils.TimeoutMillis;
import io.github.ovso.righttoknow.ui.base.IBuilder;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import timber.log.Timber;

public class VioRequest implements LifecycleObserver {
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private final static String URL_VIOLATION =
      "http://info.childcare.go.kr/info/cfvp/VioltfcltySlL.jsp?limit=500";
  private final static String URL_VIOLATORS =
      "http://info.childcare.go.kr/info/cfvp/VioltactorSlL.jsp?limit=500";
  private final static String URL_CERTIFIED =
      "http://info.childcare.go.kr/info/cera/community/notice/CertNoticeSlPL.jsp?limit=500";
  private SchedulersFacade schedulersFacade = new SchedulersFacade();

  private OnVioDataLoadCompleteListener onVioDataLoadCompleteListener;
  private VioData vioData;

  private VioRequest(Builder builder) {
    onVioDataLoadCompleteListener = builder.listener;
    vioData = builder.vioData;
  }

  public void execute() {
    reqCertified(URL_CERTIFIED);
    reqViolators(URL_VIOLATORS);
    reqViolation(URL_VIOLATION);
  }

  private void reqViolation(String url) {
    Timber.d("start reqViolation");
    Single.fromCallable(() -> Violation.toObjects(getDoc(url)))
        .subscribeOn(schedulersFacade.io())
        .subscribe(new SingleObserver<List<Violation>>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(List<Violation> $violations) {
            List<Observable<ViolationContents>> observables = new ArrayList<>();
            for (Violation violation : $violations) {
              Observable<ViolationContents> contentsObservable =
                  Observable.fromCallable(
                      () -> ViolationContents.toObject(getDoc(violation.link)))
                      .map(contents -> violation.contents = contents);
              observables.add(contentsObservable);
            }
            Observable.concat(observables)
                .subscribe(new Observer<ViolationContents>() {
                  @Override public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                  }

                  @Override public void onNext(ViolationContents contents) {

                  }

                  @Override public void onError(Throwable e) {
                    Timber.d(e);
                    error(e);
                  }

                  @Override public void onComplete() {
                    Timber.d("reqViolation complete");
                    ViolationData violationData = new ViolationData();
                    violationData.date = DateTime.now().toString();
                    violationData.items = $violations;
                    vioData.violation = violationData;
                    vioData.date = DateTime.now().toString();

                    completeLoad();
                  }
                });
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
            error(e);
          }
        });
  }

  private void completeLoad() {
    if (onVioDataLoadCompleteListener != null) {
      if (vioData.violation != null && vioData.violator != null && vioData.certified != null) {
        Timber.d("complete full data");
        onVioDataLoadCompleteListener.onComplete(vioData);
      }
      //onVioDataLoadCompleteListener.onError("ㅋㅋㅋㅋㅋ");
    }
  }

  private void error(Throwable t) {
    if (onVioDataLoadCompleteListener != null) {
      String msg = t.getMessage();
      onVioDataLoadCompleteListener.onError(msg);
    }
  }

  private void reqViolators(String url) {
    Timber.d("start reqViolator");
    Single.fromCallable(() -> Violator.toObjects(getDoc(url)))
        .subscribeOn(schedulersFacade.io())
        .subscribe(new SingleObserver<List<Violator>>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(List<Violator> $violators) {
            List<Observable<ViolatorContents>> observables = new ArrayList<>();
            for (Violator violator : $violators) {

              Observable<ViolatorContents> contentsObservable =
                  Observable.fromCallable(
                      () -> ViolatorContents.toObject(getDoc(violator.link)))
                      .map(contents -> violator.contents = contents);

              observables.add(contentsObservable);
            }
            Observable.concat(observables)
                .subscribe(new Observer<ViolatorContents>() {
                  @Override public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                  }

                  @Override public void onNext(ViolatorContents violatorContents) {

                  }

                  @Override public void onError(Throwable e) {
                    error(e);
                  }

                  @Override public void onComplete() {
                    Timber.d("reqViolator complete");

                    ViolatorData violatorData = new ViolatorData();
                    violatorData.date = DateTime.now().toString();
                    violatorData.items = $violators;
                    vioData.violator = violatorData;

                    completeLoad();
                  }
                });
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
            error(e);
          }
        });
  }

  private void reqCertified(String url) {
    Timber.d("start reqCertified");
    Single.fromCallable(() -> Certified.toObjects(getDoc(url)))
        .subscribeOn(schedulersFacade.io())
        .subscribe(new SingleObserver<List<Certified>>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(List<Certified> $certifieds) {
            Timber.d("reqCertified complete");
            CertifiedData certifiedData = new CertifiedData();
            certifiedData.date = DateTime.now().toString();
            certifiedData.items = $certifieds;
            vioData.certified = certifiedData;
            completeLoad();
          }

          @Override public void onError(Throwable e) {
            error(e);
          }
        });
  }

  private Document getDoc(String url) throws Exception {
    return Jsoup.connect(url).timeout(TimeoutMillis.JSOUP.getValue()).get();
  }

  public interface OnVioDataLoadCompleteListener {
    void onComplete(VioData vioData);

    void onError(String msg);
  }

  public static class Builder implements IBuilder<VioRequest> {
    @Setter @Accessors(chain = true) private OnVioDataLoadCompleteListener listener;
    private VioData vioData;

    @Override public VioRequest build() {
      vioData = new VioData();
      return new VioRequest(this);
    }
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  private void clear() {
    compositeDisposable.clear();
  }
}

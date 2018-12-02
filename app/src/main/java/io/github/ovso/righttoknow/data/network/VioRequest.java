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
    Single.fromCallable(() -> Violation.toObjects(getDocViolation(url)))
        .subscribeOn(schedulersFacade.io())
        .subscribe(new SingleObserver<List<Violation>>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(List<Violation> $violations) {
            if (!$violations.isEmpty()) {
              List<Observable<ViolationContents>> observables = new ArrayList<>();
              for (Violation violation : $violations) {
                Observable<ViolationContents> contentsObservable =
                    Observable.fromCallable(
                        () -> ViolationContents.toObject(getDocViolation(violation.link)))
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
                      error(e);
                    }

                    @Override public void onComplete() {
                      Timber.d("complete reqViolation");
                      ViolationData violationData = new ViolationData();
                      violationData.date = DateTime.now().toString();
                      violationData.items = $violations;
                      vioData.violation = violationData;
                      vioData.date = DateTime.now().toString();

                      completeLoad();
                    }
                  });
            } else {
              error(new RuntimeException("Empty $certifieds"));
            }
          }

          @Override public void onError(Throwable e) {
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
    }
  }

  private synchronized void error(Throwable t) {
    Timber.d(t);
    compositeDisposable.clear();
    if (onVioDataLoadCompleteListener != null) {
      String msg = t.getMessage();
      onVioDataLoadCompleteListener.onError(msg);
      onVioDataLoadCompleteListener = null;
    }
  }

  private void reqViolators(String url) {
    Timber.d("start reqViolator");
    Single.fromCallable(() -> Violator.toObjects(getDocViolators(url)))
        .subscribeOn(schedulersFacade.io())
        .subscribe(new SingleObserver<List<Violator>>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(List<Violator> $violators) {
            if (!$violators.isEmpty()) {
              List<Observable<ViolatorContents>> observables = new ArrayList<>();
              for (Violator violator : $violators) {

                Observable<ViolatorContents> contentsObservable =
                    Observable.fromCallable(
                        () -> ViolatorContents.toObject(getDocViolators(violator.link)))
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
                      Timber.d("complete reqViolator");

                      ViolatorData violatorData = new ViolatorData();
                      violatorData.date = DateTime.now().toString();
                      violatorData.items = $violators;
                      vioData.violator = violatorData;

                      completeLoad();
                    }
                  });
            } else {
              error(new RuntimeException("Empty violators"));
            }
          }

          @Override public void onError(Throwable e) {
            error(e);
          }
        });
  }

  private void reqCertified(String url) {
    Timber.d("start reqCertified");
    Single.fromCallable(() -> Certified.toObjects(getDocCertified(url)))
        .subscribeOn(schedulersFacade.io())
        .subscribe(new SingleObserver<List<Certified>>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(List<Certified> $certifieds) {
            if (!$certifieds.isEmpty()) {
              Timber.d("complete reqCertified");
              CertifiedData certifiedData = new CertifiedData();
              certifiedData.date = DateTime.now().toString();
              certifiedData.items = $certifieds;
              vioData.certified = certifiedData;
              completeLoad();
            } else {
              error(new RuntimeException("Empty $certifieds"));
            }
          }

          @Override public void onError(Throwable e) {
            error(e);
          }
        });
  }

  private synchronized Document getDocViolation(String url) throws Exception {
    return Jsoup.connect(url).timeout(TimeoutMillis.JSOUP.getValue()).get();
  }

  private synchronized Document getDocViolators(String url) throws Exception {
    return Jsoup.connect(url).timeout(TimeoutMillis.JSOUP.getValue()).get();
  }

  private synchronized Document getDocCertified(String url) throws Exception {
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

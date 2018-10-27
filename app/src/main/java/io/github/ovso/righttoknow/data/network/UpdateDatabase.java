package io.github.ovso.righttoknow.data.network;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ovso.righttoknow.BuildConfig;
import io.github.ovso.righttoknow.data.network.model.violation.Violation;
import io.github.ovso.righttoknow.data.network.model.violation.ViolationContents;
import io.github.ovso.righttoknow.data.network.model.violation.ViolationData;
import io.github.ovso.righttoknow.data.network.model.violators.Violators;
import io.github.ovso.righttoknow.data.network.model.violators.ViolatorsContents;
import io.github.ovso.righttoknow.data.network.model.violators.ViolatorsData;
import io.github.ovso.righttoknow.framework.utils.TimeoutMillis;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import timber.log.Timber;

public class UpdateDatabase {
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private final static String URL_VIOLATION =
      "http://info.childcare.go.kr/info/cfvp/VioltfcltySlL.jsp?limit=500";
  private final static String URL_VIOLATORS =
      "http://info.childcare.go.kr/info/cfvp/VioltactorSlL.jsp?limit=500";
  private final static String URL_CERTIFIED =
      "http://info.childcare.go.kr/info/cera/community/notice/CertNoticeSlPL.jsp?limit=500";
  private SchedulersFacade schedulersFacade = new SchedulersFacade();

  public void update() {
    if (BuildConfig.DEBUG) {
      FirebaseDatabase database = FirebaseDatabase.getInstance();
      DatabaseReference myRef = database.getReference("violation");
      //reqViolation(URL_VIOLATION);
      reqViolators(URL_VIOLATORS);
      reqCertified(URL_CERTIFIED);
    }
  }

  private void reqViolation(String url) {

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
            AtomicInteger atomic = new AtomicInteger();
            Disposable disposable = Observable.concat(observables)
                .subscribe(
                    contents -> Timber.d(atomic.incrementAndGet() + ""),
                    e -> Timber.d(e),
                    () -> {
                      Timber.d("violations size = " + $violations.size());
                      ViolationData data = new ViolationData();
                      data.date = new Timestamp(new Date().getTime()).toString();
                      data.items = $violations;
                      FirebaseDatabase.getInstance()
                          .getReference("violation")
                          .setValue(data, (databaseError, databaseReference) -> Timber.d(
                              databaseError + ", " + databaseReference.getKey()));
                      Timber.d(Thread.currentThread().getName());
                    });

            compositeDisposable.add(disposable);
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
          }
        });
  }

  private void reqViolators(String url) {
    Single.fromCallable(() -> Violators.toObjects(getDoc(url)))
        .subscribeOn(schedulersFacade.io())
        .subscribe(new SingleObserver<List<Violators>>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(List<Violators> $violators) {
            List<Observable<ViolatorsContents>> observables = new ArrayList<>();
            for (Violators violator : $violators) {

              Observable<ViolatorsContents> contentsObservable =
                  Observable.fromCallable(
                      () -> ViolatorsContents.toObject(getDoc(violator.link)))
                      .map(contents -> violator.contents = contents);

              observables.add(contentsObservable);
            }
            AtomicInteger atomic = new AtomicInteger();
            Disposable disposable = Observable.concat(observables)
                .subscribe(
                    contents -> Timber.d(atomic.incrementAndGet() + ""),
                    e -> Timber.d(e),
                    () -> {
                      Timber.d("violators size = " + $violators.size());
                      ViolatorsData data = new ViolatorsData();
                      data.date = new Timestamp(new Date().getTime()).toString();
                      data.items = $violators;
                      FirebaseDatabase.getInstance()
                          .getReference("violator")
                          .setValue(data, (databaseError, databaseReference) -> Timber.d(
                              databaseError + ", " + databaseReference.getKey()));
                      Timber.d(Thread.currentThread().getName());
                    });

            compositeDisposable.add(disposable);
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
          }
        });
  }

  private void reqCertified(String url) {
  }

  private Document getDoc(String url) throws Exception {
    return Jsoup.connect(url).timeout(TimeoutMillis.JSOUP.getValue()).get();
  }
}

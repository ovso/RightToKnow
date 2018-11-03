package io.github.ovso.righttoknow.data.network;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.github.ovso.righttoknow.BuildConfig;
import io.github.ovso.righttoknow.data.network.model.certified.Certified;
import io.github.ovso.righttoknow.data.network.model.certified.CertifiedData;
import io.github.ovso.righttoknow.data.network.model.violation.Violation;
import io.github.ovso.righttoknow.data.network.model.violation.ViolationContents;
import io.github.ovso.righttoknow.data.network.model.violation.ViolationData;
import io.github.ovso.righttoknow.data.network.model.violators.Violator;
import io.github.ovso.righttoknow.data.network.model.violators.ViolatorContents;
import io.github.ovso.righttoknow.data.network.model.violators.ViolatorData;
import io.github.ovso.righttoknow.framework.utils.TimeoutMillis;
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
import org.joda.time.DateTime;
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
      getLastUpdateDay("certified", () -> reqCertified(URL_CERTIFIED));
      getLastUpdateDay("violator", () -> reqViolators(URL_VIOLATORS));
      getLastUpdateDay("violation", () -> reqViolation(URL_VIOLATION));
    }
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
                  }

                  @Override public void onComplete() {
                    ViolationData value = new ViolationData();
                    value.date = DateTime.now().toString();
                    value.items = $violations;
                    FirebaseDatabase.getInstance()
                        .getReference("violation")
                        .setValue(value, (databaseError, databaseReference) -> {
                          Timber.d("violation complete");
                          if (completeListener != null) {
                            completeListener.onComplete();
                          }
                        });
                  }
                });
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
          }
        });
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

                  }

                  @Override public void onComplete() {
                    ViolatorData value = new ViolatorData();
                    value.date = DateTime.now().toString();
                    value.items = $violators;
                    FirebaseDatabase.getInstance()
                        .getReference("violator")
                        .setValue(value, (databaseError, databaseReference) -> {
                          Timber.d("violator complete");
                        });
                  }
                });
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
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
            CertifiedData value = new CertifiedData();
            value.date = DateTime.now().toString();
            value.items = $certifieds;
            FirebaseDatabase.getInstance()
                .getReference("certified")
                .setValue(value, (databaseError, databaseReference) -> {
                  Timber.d("certified complete");
                });
          }

          @Override public void onError(Throwable e) {

          }
        });
  }

  private Document getDoc(String url) throws Exception {
    return Jsoup.connect(url).timeout(TimeoutMillis.JSOUP.getValue()).get();
  }

  private synchronized void getLastUpdateDay(String path, UpdateCheckListener l) {
    FirebaseDatabase.getInstance()
        .getReference(path)
        .child("date").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        String date = dataSnapshot.getValue().toString();
        DateTime fbDateTime = DateTime.parse(date);
        DateTime dateTime = new DateTime();
        if (l != null && dateTime.getMillis() > fbDateTime.getMillis()) {
          l.onUpdate();
        }
        //Timber.d("fb date time = " + fbDateTime.getMillis());
        //Timber.d("nw date time = " + dateTime.getMillis());
      }

      @Override public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  private interface UpdateCheckListener {
    void onUpdate();
  }
  @Setter private CompleteListener completeListener;

  public interface CompleteListener {
    void onComplete();
  }
}

package io.github.ovso.righttoknow.data.network;

import android.support.annotation.NonNull;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
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
    //if (BuildConfig.DEBUG) {
    //  getLastUpdateDay("violation", () -> reqViolation(URL_VIOLATION));
    //  getLastUpdateDay("violator", () -> reqViolators(URL_VIOLATORS));
    //  getLastUpdateDay("certified", () -> reqCertified(URL_CERTIFIED));
    //}

    reqDatabase();
  }

  private void reqDatabase() {
    DatabaseReference databaseReference =
        FirebaseDatabase.getInstance().getReference("violation").child("items");

    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        String s = dataSnapshot.getValue().toString();
        //Timber.d("s = " + s);
      }

      @Override public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
    /*
    RxFirebaseDatabase.data(databaseReference)
        .observeOn(schedulersFacade.io())
        .subscribeOn(schedulersFacade.ui())
        .subscribe(new SingleObserver<DataSnapshot>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(DataSnapshot dataSnapshot) {
            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
          }

          @Override public void onError(Throwable e) {

          }
        });
    */

    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

      }

      @Override public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
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
            Disposable disposable = Observable.concat(observables)
                .subscribe(
                    contents -> {
                    },
                    e -> Timber.d(e),
                    () -> {
                      ViolationData value = new ViolationData();
                      value.date = new Timestamp(new Date().getTime()).toString();
                      value.items = $violations;
                      FirebaseDatabase.getInstance()
                          .getReference("violation")
                          .setValue(value);
                      Timber.d("violation date = " + value.date);
                      Timber.d("violation size = " + value.items.size());
                    });

            compositeDisposable.add(disposable);
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
            Disposable disposable = Observable.concat(observables)
                .subscribe(
                    contents -> {

                    },
                    e -> Timber.d(e),
                    () -> {
                      ViolatorData value = new ViolatorData();
                      value.date = new Timestamp(new Date().getTime()).toString();
                      value.items = $violators;
                      FirebaseDatabase.getInstance()
                          .getReference("violator")
                          .setValue(value);
                      Timber.d("violator date = " + value.date);
                      Timber.d("violator size = " + value.items.size());
                    });

            compositeDisposable.add(disposable);
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
          }
        });
  }

  private void reqCertified(String url) {
    Timber.d("start reqCertified");
    Disposable disposable = Single.fromCallable(() -> Certified.toObjects(getDoc(url)))
        .subscribeOn(schedulersFacade.io())
        .subscribe(
            $certifieds -> {
              CertifiedData value = new CertifiedData();
              value.date = new Timestamp(new Date().getTime()).toString();
              value.items = $certifieds;
              FirebaseDatabase.getInstance()
                  .getReference("certified")
                  .setValue(value);
              Timber.d("certified date = " + value.date);
              Timber.d("certified size = " + value.items.size());
            },
            throwable -> Timber.d(throwable));
    compositeDisposable.add(disposable);
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
        DateTime fbDateTime =
            DateTime.parse(date, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.yyy"));
        int fbDay = fbDateTime.dayOfMonth().get();

        DateTime dateTime = new DateTime();
        int dayOfMonth = dateTime.getDayOfMonth();
        if (l != null && dayOfMonth > fbDay) {
          l.onUpdate();
        }
      }

      @Override public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  private interface UpdateCheckListener {
    void onUpdate();
  }
}

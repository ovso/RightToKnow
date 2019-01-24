package io.github.ovso.righttoknow.ui.splash;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.github.ovso.righttoknow.data.network.VioRequest;
import io.github.ovso.righttoknow.data.network.model.VioData;
import io.github.ovso.righttoknow.data.network.model.certified.VioDataWrapper;
import io.github.ovso.righttoknow.ui.splash.vo.SplashArguments;
import io.github.ovso.righttoknow.utils.DateUtils;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import org.joda.time.DateTime;
import timber.log.Timber;

public class SplashPresenterImpl implements SplashPresenter {

  private SplashPresenter.View view;
  private VioRequest vioRequest;
  private SchedulersFacade schedulers;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private VioDataWrapper vioDataWrapper;

  SplashPresenterImpl(SplashArguments args) {
    view = args.getView();
    vioRequest = args.getVioRequest();
    schedulers = args.getSchedulers();
    vioDataWrapper = args.getVioDataWrapper();
  }

  @Override public void onCreate() {
    handleReq();
  }

  private void handleReq() {
    getRef().child("date").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        String date = dataSnapshot.getValue().toString();
        DateTime fibDate = DateTime.parse(date);
        DateTime nowDate = DateTime.now();
        long diffOfDate = DateUtils.diffOfDate(fibDate, nowDate);
        if (diffOfDate > 2) {
          if (vioDataWrapper.vioData != null) {
            reqFirebaseDate();
          } else {
            try {
              vioRequest.execute();
            } catch (Exception e) {
              onError(e.getMessage());
            }
          }
        } else {
          reqFirebaseDate();
        }
      }

      @Override public void onCancelled(@NonNull DatabaseError databaseError) {
        Timber.d("databaseError = " + databaseError);
      }
    });
  }

  @Override public void onComplete(VioData $vioData) {
    vioDataWrapper.vioData = $vioData;
    updateFirebase();
    view.navigateToMain();
  }

  private void updateFirebase() {
    getRef().child("date").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        String date = dataSnapshot.getValue().toString();
        DateTime fibDate = DateTime.parse(date);
        DateTime nowDate = DateTime.now();
        Timber.d("fibdate = " + fibDate.getDayOfMonth());
        Timber.d("nowdate = " + nowDate.getDayOfMonth());
        if (fibDate.getDayOfMonth() != nowDate.getDayOfMonth()) {
          getRef().setValue(vioDataWrapper.vioData,
              ((databaseError, databaseReference) -> Timber.d("updateFirebase complete")));
        }
      }

      @Override public void onCancelled(@NonNull DatabaseError databaseError) {
        Timber.d("databaseError = " + databaseError);
      }
    });
  }

  @Override public void onError(String msg) {
    reqFirebaseDate();
  }

  private void reqFirebaseDate() {
    RxFirebaseDatabase.data(getRef())
        .subscribeOn(schedulers.io())
        .map(dataSnapshot -> dataSnapshot.getValue(VioData.class))
        .subscribe(new SingleObserver<VioData>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(VioData vioData) {
            vioDataWrapper.vioData = vioData;
            view.navigateToMain();
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
          }
        });
  }

  private DatabaseReference getRef() {
    return FirebaseDatabase.getInstance()
        .getReference("vio_data");
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  public void onDestroy() {
    compositeDisposable.clear();
  }
}
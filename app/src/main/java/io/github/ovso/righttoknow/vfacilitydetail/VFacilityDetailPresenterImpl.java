package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.vfacilitydetail.model.VioFacDe;
import io.github.ovso.righttoknow.vfacilitydetail.model.ViolatorDe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import org.jsoup.Jsoup;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 8. 2
 */

public class VFacilityDetailPresenterImpl implements VFacilityDetailPresenter {
  private VFacilityDetailPresenter.View view;
  private VFacilityDetailModel model;

  VFacilityDetailPresenterImpl(VFacilityDetailPresenter.View view) {
    this.view = view;
    model = new VFacilityDetailModel(MyApplication.getInstance());
  }

  @Override public void onCreate(Bundle savedInstanceState, Intent intent) {
    view.setListener();
    view.setSupportActionBar();
    view.setTitle(MyApplication.getInstance().getString(R.string.title_vioation_facility_inquiry));
    view.showLoading();
    view.showAd();
    req(intent);
  }

  private void req(Intent intent) {
    if (intent.hasExtra("vio_fac_link")) {
      final String link = intent.getStringExtra("vio_fac_link");
      Observable.fromCallable(
          () -> VioFacDe.getContents(VioFacDe.convertToItem(Jsoup.connect(link).get())))
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(o -> {
            view.showContents(o);
            view.hideLoading();
          }, throwable -> {
            Timber.d(throwable);
            view.showMessage(R.string.error_server);
            view.hideLoading();
          });
    } else if (intent.hasExtra("violator_link")) {
      final String link = intent.getStringExtra("violator_link");
      Observable.fromCallable(
          () -> ViolatorDe.getContents(ViolatorDe.convertToItem(Jsoup.connect(link).get())))
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(o -> {
            view.showContents(o);
            view.hideLoading();
          }, throwable -> {
            Timber.d(throwable);
            view.showMessage(R.string.error_server);
            view.hideLoading();
          });
    }

  }
  @Override public void onDestroy() {
  }

  @Override public void onLocationClick() {

  }

  @Override public void onRefresh(Intent intent) {
    req(intent);
  }

  @Override public void onBackPressed() {
  }
}
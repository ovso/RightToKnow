package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.vfacilitydetail.model.VioFacDe;
import io.github.ovso.righttoknow.vfacilitydetail.model.ViolatorDe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import org.jsoup.Jsoup;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 8. 2
 */

public class VFacilityDetailPresenterImpl implements VFacilityDetailPresenter {
  private VFacilityDetailPresenter.View view;

  VFacilityDetailPresenterImpl(VFacilityDetailPresenter.View view) {
    this.view = view;
  }

  @Override public void onCreate(Bundle savedInstanceState, Intent intent) {
    view.setListener();
    view.setSupportActionBar();
    view.showLoading();
    view.showAd();
    req(intent);
  }

  private String address;
  private Disposable disposable;
  private void req(Intent intent) {
    if (intent.hasExtra("vio_fac_link")) {
      final String link = intent.getStringExtra("vio_fac_link");
      disposable = Observable.fromCallable(new Callable<String>() {
        @Override public String call() throws Exception {
          VioFacDe vioFacDe = VioFacDe.convertToItem(Jsoup.connect(link).get());
          address = vioFacDe.getAddress();
          return VioFacDe.getContents(vioFacDe);
        }
      }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
        view.showContents(o);
        view.hideLoading();
      }, throwable -> {
        Timber.d(throwable);
        view.showMessage(R.string.error_server);
        view.hideLoading();
      });
    } else if (intent.hasExtra("violator_link")) {
      final String link = intent.getStringExtra("violator_link");
      disposable = Observable.fromCallable(new Callable<String>() {
        @Override public String call() throws Exception {
          ViolatorDe violatorDe = ViolatorDe.convertToItem(Jsoup.connect(link).get());
          address = violatorDe.getAddress();
          return ViolatorDe.getContents(violatorDe);
        }
      }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
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
    if (disposable != null) {
      disposable.dispose();
    }
  }

  @Override public void onMapClick(Intent intent) {
    if (!TextUtils.isEmpty(address)) {
      int beginIndex = address.indexOf(")") + 1;
      int endIndex = address.indexOf("(", 1) - 1;
      String subAddress = address.substring(beginIndex, endIndex);
      Timber.d("address = " + address);
      Timber.d("subAddress = " + subAddress);

      Timber.d("addressssssssss = " + subAddress.substring(subAddress.length() - 10, subAddress.length()));

      view.navigateToMap(subAddress);
    } else {
      view.showMessage(R.string.error_server);
    }
  }

  @Override public void onRefresh(Intent intent) {
    req(intent);
  }

  @Override public void onBackPressed() {
  }
}
package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.common.AddressUtils;
import io.github.ovso.righttoknow.network.GeocodeNetwork;
import io.github.ovso.righttoknow.network.model.GeometryLocation;
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
  private GeocodeNetwork geocodeNetwork;

  VFacilityDetailPresenterImpl(VFacilityDetailPresenter.View view) {
    this.view = view;
    geocodeNetwork = new GeocodeNetwork(MyApplication.getInstance().getApplicationContext(),
        GeocodeNetwork.GEOCODING_BASE_URL);
  }

  @Override public void onCreate(Bundle savedInstanceState, Intent intent) {
    view.setListener();
    view.hideButton();
    view.setSupportActionBar();
    view.showLoading();
    view.showAd();
    req(intent);
  }

  private String fullAddress;
  private Disposable disposable;
  private String facName;
  private void req(Intent intent) {
    if (intent.hasExtra("vio_fac_link")) {
      final String link = intent.getStringExtra("vio_fac_link");
      disposable = Observable.fromCallable(new Callable<String>() {
        @Override public String call() throws Exception {
          VioFacDe vioFacDe = VioFacDe.convertToItem(Jsoup.connect(link).get());
          facName = vioFacDe.getVioFacName();
          Timber.d("facName = " + facName);
          fullAddress = vioFacDe.getAddress();
          return VioFacDe.getContents(vioFacDe);
        }
      }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
        view.showContents(o);
        view.showButton();
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
          facName = violatorDe.getFacName();
          Timber.d("facName = " + facName);
          fullAddress = violatorDe.getAddress();
          return ViolatorDe.getContents(violatorDe);
        }
      }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(o -> {
        view.showContents(o);
        view.showButton();
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
  //37.5652894,126.8494635 서울
  //locations[0] = 37.5652894;
  //locations[1] = 126.8494635;
  private final static double LAT_SEOUL = 37.5652894;
  private final static double LNG_SEOUL = 126.8494635;

  @Override public void onMapClick(Intent intent) {
    view.showLoading();
    if (!TextUtils.isEmpty(fullAddress)) {

      try {
        String address = AddressUtils.removeBracket(fullAddress);
        Timber.d("address = " + address);

        disposable = geocodeNetwork.getGoogleGeocode(address)
            .map(googleGeocode -> {
              double[] locations = new double[2];
              if (googleGeocode.getStatus().contains("OK")) {
                GeometryLocation location =
                    googleGeocode.getResults().get(0).getGeometry().getLocation();
                locations[0] = location.getLat();
                locations[1] = location.getLng();
              } else {
                locations[0] = LAT_SEOUL;
                locations[1] = LNG_SEOUL;
              }
              return locations;
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(locations -> {
              if(TextUtils.isEmpty(facName)) {
                new NullPointerException("facName is null");
              }
              view.navigateToMap(locations, facName);
              view.hideLoading();
            }, throwable -> {
              Timber.e(throwable);
              view.showMessage(R.string.error_not_found_address);
              view.hideLoading();
            });
      } catch (Exception e) {
        Timber.e(e);
        view.showMessage(R.string.error_not_found_address);
        view.hideLoading();
      }
    } else {
      view.showMessage(R.string.error_not_found_address);
      view.hideLoading();
    }
  }

  @Override public void onRefresh(Intent intent) {
    req(intent);
  }

  @Override public void onBackPressed() {
  }
}
package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.common.AddressUtils;
import io.github.ovso.righttoknow.network.GeocodeNetwork;
import io.github.ovso.righttoknow.network.model.GeometryLocation;
import io.github.ovso.righttoknow.network.model.GoogleGeocode;
import io.github.ovso.righttoknow.vfacilitydetail.model.VioFacDe;
import io.github.ovso.righttoknow.vfacilitydetail.model.ViolatorDe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
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
    view.setSupportActionBar();
    view.showLoading();
    view.showAd();
    req(intent);
  }

  private String fullAddress;
  private Disposable disposable;

  private void req(Intent intent) {
    if (intent.hasExtra("vio_fac_link")) {
      final String link = intent.getStringExtra("vio_fac_link");
      disposable = Observable.fromCallable(new Callable<String>() {
        @Override public String call() throws Exception {
          VioFacDe vioFacDe = VioFacDe.convertToItem(Jsoup.connect(link).get());
          fullAddress = vioFacDe.getAddress();
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
          fullAddress = violatorDe.getAddress();
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
  //37.5652894,126.8494635 서울
  //locations[0] = 37.5652894;
  //locations[1] = 126.8494635;

  @Override public void onMapClick(Intent intent) {
    view.showLoading();
    String intetFacName = null;
    if (!TextUtils.isEmpty(fullAddress)) {

      try {
        String address = AddressUtils.removeBracket(fullAddress);
        Timber.d("address = " + address);

        if (intent.hasExtra("facName")) {
          intetFacName = intent.getStringExtra("facName");
        } else {
          intetFacName = "서울";
        }
        final String facName = intetFacName;
        /*
        double[] locationArray = AddressUtils.getFromLocation(MyApplication.getInstance(), address);
        if (!ObjectUtils.isEmpty(locationArray)) {
          view.navigateToMap(locationArray, facName);
        } else {
          view.showMessage(R.string.error_not_found_address);
        }
        view.hideLoading();
        */
        disposable = geocodeNetwork.getGoogleGeocode(address)
            .map(new Function<GoogleGeocode, double[]>() {
              @Override public double[] apply(GoogleGeocode googleGeocode) throws Exception {
                double[] locations = new double[2];
                  Timber.d("status = " + googleGeocode.getStatus());
                  GeometryLocation location =
                      googleGeocode.getResults().get(0).getGeometry().getLocation();
                  locations[0] = location.getLat();
                  locations[1] = location.getLng();
                  return locations;
              }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(locations -> {
              Timber.d("locations = " + locations[0] + ", " + locations[1]);
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
      view.showMessage(R.string.error_server);
      view.hideLoading();
    }
  }

  @Override public void onRefresh(Intent intent) {
    req(intent);
  }

  @Override public void onBackPressed() {
  }
}
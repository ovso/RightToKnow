package io.github.ovso.righttoknow.ui.violation_contents;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.network.model.violation.ViolationContents;
import io.github.ovso.righttoknow.framework.network.GeocodeNetwork;
import io.github.ovso.righttoknow.framework.network.model.GeometryLocation;
import io.github.ovso.righttoknow.framework.network.model.GoogleGeocode;
import io.github.ovso.righttoknow.framework.utils.AddressUtils;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ViolationContentsPresenterImpl implements ViolationContentsPresenter {
  private final static double LAT_SEOUL = 37.5652894;
  private final static double LNG_SEOUL = 126.8494635;
  private ViolationContentsPresenter.View view;
  private GeocodeNetwork geocodeNetwork;
  private String fullAddress;
  private Disposable disposable;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private String facName;
  private SchedulersFacade schedulersFacade;
  private ViolationContents contents;

  ViolationContentsPresenterImpl(
      ViolationContentsPresenter.View $view,
      SchedulersFacade $schedulersFacade,
      ViolationContents $contents) {
    this.view = $view;
    geocodeNetwork = new GeocodeNetwork(App.getInstance().getApplicationContext(),
        GeocodeNetwork.GEOCODING_BASE_URL);
    schedulersFacade = $schedulersFacade;
    contents = $contents;
  }

  @Override public void onCreate(Bundle savedInstanceState, Intent intent) {
    view.setListener();
    view.hideButton();
    view.setSupportActionBar();

    showContents();
  }

  private void showContents() {
    Single.fromCallable(() -> ViolationContents.toFormatedText(contents))
        .subscribeOn(schedulersFacade.io())
        .observeOn(schedulersFacade.ui())
        .subscribe(
            new SingleObserver<String>() {
              @Override public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
              }

              @Override public void onSuccess(String contents) {
                view.showContents(contents);
                view.showButton();
                view.hideLoading();
              }

              @Override public void onError(Throwable e) {
                Timber.d(e);
                view.hideLoading();
              }
            });
  }

  @Override public void onDestroy() {
    if (disposable != null) {
      disposable.dispose();
    }
    compositeDisposable.clear();
  }

  @Override public void onMapClick(Intent intent) {
    view.showLoading();
    if (!TextUtils.isEmpty(fullAddress)) {

      try {
        String address = AddressUtils.removeBracket(fullAddress);
        Timber.d("address = " + address);

        disposable = geocodeNetwork.getGoogleGeocode(address)
            .map(new Function<GoogleGeocode, double[]>() {
              @Override public double[] apply(GoogleGeocode googleGeocode) throws Exception {
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
              }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<double[]>() {
              @Override public void accept(double[] locations) throws Exception {
                if (TextUtils.isEmpty(facName)) {
                  new NullPointerException("facName is null");
                }
                view.navigateToMap(locations, facName);
                view.hideLoading();
              }
            }, new Consumer<Throwable>() {
              @Override public void accept(Throwable throwable) throws Exception {
                Timber.e(throwable);
                view.showMessage(R.string.error_not_found_address);
                view.hideLoading();
              }
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
    showContents();
  }
}
package io.github.ovso.righttoknow.ui.main.violationfacility;

import android.os.Bundle;
import android.text.TextUtils;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.framework.utils.Constants;
import io.github.ovso.righttoknow.framework.utils.TimeoutMillis;
import io.github.ovso.righttoknow.ui.main.violationfacility.model.VioFac;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import timber.log.Timber;

public class ViolationFacilityPresenterImpl implements ViolationFacilityPresenter {

  private ViolationFacilityPresenter.View view;
  private BaseAdapterDataModel<VioFac> adapterDataModel;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private String connectUrl;

  ViolationFacilityPresenterImpl(ViolationFacilityPresenter.View view) {
    this.view = view;
    connectUrl = Constants.BASE_URL + Constants.FAC_LIST_PATH_QUERY;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    req();
  }

  private void req() {
    view.showLoading();

    compositeDisposable.add(Maybe.fromCallable(
        () -> VioFac.convertToItems(
            Jsoup.connect(connectUrl).timeout(TimeoutMillis.JSOUP.getValue()).get()))
        .onErrorReturn(throwable -> new ArrayList<>())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          if (items.isEmpty()) {
            view.showMessage(R.string.error_server);
          }
          view.hideLoading();
        }, throwable -> {
          Timber.e(throwable);
          view.showMessage(R.string.error_server);
          view.hideLoading();
        }));
  }

  @Override public void setAdapterModel(BaseAdapterDataModel<VioFac> adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onRecyclerItemClick(VioFac vioFac) {
    String webLink = vioFac.getLink();
    String address = vioFac.getAddress();
    if (webLink != null) {
      view.navigateToViolationFacilityDetail(webLink, address);
    } else {
      view.showMessage(R.string.error_server);
    }
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    view.setSearchResultText(R.string.empty);
    req();
  }

  @Override public void onSearchQuery(final String query) {
    view.showLoading();
    adapterDataModel.clear();
    view.refresh();

    compositeDisposable.add(Observable.fromCallable(() -> {
      List<VioFac> items = VioFac.convertToItems(
          Jsoup.connect(connectUrl).timeout(TimeoutMillis.JSOUP.getValue()).get());
      return VioFac.searchResultItems(query, items);
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
        items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          Timber.d(throwable);
          view.hideLoading();
        }));
  }

  @Override public void onOptionsItemSelected(int itemId) {
    final String sido = Sido.getSido(itemId, App.getInstance());
    if (!TextUtils.isEmpty(sido)) {
      onSearchQuery(sido);
    }
  }

  @Override public void onDestroyView() {
    compositeDisposable.clear();
  }
}
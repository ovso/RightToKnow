package io.github.ovso.righttoknow.violator;

import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.violator.model.Violator;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import org.jsoup.Jsoup;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 8. 3
 */

public class ViolatorFragmentPresenterImpl implements ViolatorFragmentPresenter {
  private ViolatorFragmentPresenter.View view;
  private ViolatorAdapterDataModel adapterDataModel;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  ViolatorFragmentPresenterImpl(ViolatorFragmentPresenter.View view) {
    this.view = view;
  }

  @Override public void onActivityCreate(Bundle savedInstanceState) {
    view.showLoading();
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    req();
  }

  private void req() {
    compositeDisposable.add(Observable.fromCallable(() -> Violator.convertToItems(
        Jsoup.connect(Constants.BASE_URL + Constants.VIOLATOR_LIST_PATH_QUERY).get()))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          Timber.d(throwable);
          view.showMessage(R.string.error_server);
          view.hideLoading();
        }));
  }

  @Override public void setAdapterModel(ViolatorAdapterDataModel adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onRecyclerItemClick(Violator violator) {
    view.navigateToViolatorDetail(violator.getLink(), violator.getAddress());
  }

  @Override public void onDestroyView() {
    compositeDisposable.dispose();
    compositeDisposable.clear();
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    view.setSearchResultText(R.string.empty);
    req();
  }

  @Override public void onSearchQuery(String query) {
    view.showLoading();
    adapterDataModel.clear();
    view.refresh();
  }
}

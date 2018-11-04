package io.github.ovso.righttoknow.ui.main.violator;

import android.os.Bundle;
import android.text.TextUtils;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.data.Sido;
import io.github.ovso.righttoknow.data.network.model.VioData;
import io.github.ovso.righttoknow.data.network.model.violators.Violator;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.utils.ResourceProvider;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;
import timber.log.Timber;

public class ViolatorFragmentPresenterImpl implements ViolatorFragmentPresenter {
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private ViolatorFragmentPresenter.View view;
  private BaseAdapterDataModel<Violator> adapterDataModel;
  private SchedulersFacade schedulersFacade;
  private ResourceProvider resourceProvider;
  private VioData vioData;

  ViolatorFragmentPresenterImpl(ViolatorFragmentPresenter.View $view,
      SchedulersFacade $schedulersFacade, ResourceProvider $resourceProvider,
      BaseAdapterDataModel<Violator> $adapterDataModel,
      VioData $vioData) {
    view = $view;
    schedulersFacade = $schedulersFacade;
    resourceProvider = $resourceProvider;
    adapterDataModel = $adapterDataModel;
    vioData = $vioData;
  }

  @Override public void onActivityCreate(Bundle savedInstanceState) {
    view.showLoading();
    view.setListener();
    view.setupAdapter();
    view.setupRecyclerView();
    updateAdapter(vioData.violator.items);
  }

  private void updateAdapter(List<Violator> $items) {
    adapterDataModel.clear();
    adapterDataModel.addAll($items);
    view.refresh();
    view.hideLoading();
  }

  @Override public void setAdapterModel(ViolatorAdapterDataModel adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onRecyclerItemClick(Violator violator) {
    view.navigateToViolatorDetail(violator);
  }

  @Override public void onDestroyView() {
    compositeDisposable.clear();
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    updateAdapter(vioData.violator.items);
  }

  @Override public void onSearchQuery(final String query) {
    Observable.fromIterable(vioData.violator.items)
        .filter(item -> item.sido.contains(query))
        .toList()
        .subscribeOn(schedulersFacade.io())
        .observeOn(schedulersFacade.ui())
        .subscribe(
            new SingleObserver<List<Violator>>() {
              @Override public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
              }

              @Override public void onSuccess(List<Violator> violators) {
                updateAdapter(violators);
              }

              @Override public void onError(Throwable e) {
                Timber.d(e);
              }
            });
  }

  @Override public void onOptionsItemSelected(int itemId) {
    String sido = Sido.getSido(itemId, App.getInstance());
    if (!TextUtils.isEmpty(sido)) {
      onSearchQuery(sido);
    }
  }
}

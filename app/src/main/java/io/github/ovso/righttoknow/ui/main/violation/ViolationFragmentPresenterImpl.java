package io.github.ovso.righttoknow.ui.main.violation;

import android.os.Bundle;
import android.text.TextUtils;
import io.github.ovso.righttoknow.data.Sido;
import io.github.ovso.righttoknow.data.network.model.VioData;
import io.github.ovso.righttoknow.data.network.model.violation.Violation;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.utils.ResourceProvider;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;
import timber.log.Timber;

public class ViolationFragmentPresenterImpl implements ViolationFragmentPresenter {

  private ViolationFragmentPresenter.View view;
  private BaseAdapterDataModel<Violation> adapterDataModel;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private SchedulersFacade schedulersFacade;
  private ResourceProvider resourceProvider;
  private VioData vioData;

  ViolationFragmentPresenterImpl(ViolationFragmentPresenter.View view,
      SchedulersFacade $schedulersFacade, ResourceProvider $resourceProvider,
      VioData $vioData) {
    this.view = view;
    schedulersFacade = $schedulersFacade;
    resourceProvider = $resourceProvider;
    vioData = $vioData;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    updateAdapter(vioData.violation.items);
  }

  private void updateAdapter(List<Violation> $items) {
    adapterDataModel.clear();
    adapterDataModel.addAll($items);
    view.refresh();
    view.hideLoading();
  }

  @Override public void setAdapterModel(BaseAdapterDataModel<Violation> adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onRecyclerItemClick(Violation violation) {
    view.navigateToContents(violation);
  }

  @Override public void onRefresh() {
    updateAdapter(vioData.violation.items);
  }

  @Override public void onSearchQuery(final String query) {
    Observable.fromIterable(vioData.violation.items)
        .filter(item -> item.sido.contains(query))
        .toList()
        .subscribeOn(schedulersFacade.io())
        .observeOn(schedulersFacade.ui())
        .subscribe(new SingleObserver<List<Violation>>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(List<Violation> violations) {
            updateAdapter(violations);
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
          }
        });
  }

  @Override public void onOptionsItemSelected(int itemId) {
    final String sido = Sido.toName(itemId, resourceProvider);
    if (!TextUtils.isEmpty(sido)) {
      onSearchQuery(sido);
    }
  }

  @Override public void onDestroyView() {
    compositeDisposable.clear();
  }
}
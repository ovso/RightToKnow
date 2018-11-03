package io.github.ovso.righttoknow.ui.main.violator;

import android.os.Bundle;
import android.text.TextUtils;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.Sido;
import io.github.ovso.righttoknow.data.network.model.violators.Violator;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.utils.ResourceProvider;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class ViolatorFragmentPresenterImpl implements ViolatorFragmentPresenter {
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private ViolatorFragmentPresenter.View view;
  private BaseAdapterDataModel<Violator> adapterDataModel;
  private SchedulersFacade schedulersFacade;
  private ResourceProvider resourceProvider;

  ViolatorFragmentPresenterImpl(ViolatorFragmentPresenter.View $view,
      SchedulersFacade $schedulersFacade, ResourceProvider $resourceProvider,
      BaseAdapterDataModel<Violator> $adapterDataModel) {
    view = $view;
    schedulersFacade = $schedulersFacade;
    resourceProvider = $resourceProvider;
    adapterDataModel = $adapterDataModel;
  }

  @Override public void onActivityCreate(Bundle savedInstanceState) {
    view.showLoading();
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    reqDatabase();
  }

  private void reqDatabase() {
    view.showLoading();
    RxFirebaseDatabase.data(getRef())
        .map(v -> toList(v.getChildren().iterator()))
        .subscribeOn(schedulersFacade.io())
        .observeOn(schedulersFacade.ui())
        .subscribe(new SingleObserver<List<Violator>>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(List<Violator> violations) {
            adapterDataModel.clear();
            adapterDataModel.addAll(violations);
            view.refresh();
            view.hideLoading();
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
            view.hideLoading();
          }
        });
  }

  private List<Violator> toList(Iterator<DataSnapshot> iterator) {
    List<Violator> violators = new ArrayList<>();
    while (iterator.hasNext()) {
      Violator violator = iterator.next().getValue(Violator.class);
      violators.add(violator);
    }
    return violators;
  }

  private DatabaseReference getRef() {
    return FirebaseDatabase.getInstance().getReference("violator").child("items");
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
    view.setSearchResultText(R.string.empty);
    reqDatabase();
  }

  @Override public void onSearchQuery(final String query) {
    view.showLoading();

    RxFirebaseDatabase.data(getRef())
        .flatMap(data -> Observable.fromIterable(toList(data.getChildren().iterator()))
            .filter(item -> item.sido.contains(query))
            .toList())
        .subscribeOn(schedulersFacade.io())
        .observeOn(schedulersFacade.ui())
        .subscribe(new SingleObserver<List<Violator>>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(List<Violator> violators) {
            Timber.d("filter size = " + violators.size());
            adapterDataModel.clear();
            adapterDataModel.addAll(violators);
            view.refresh();
            view.hideLoading();
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
            view.hideLoading();
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

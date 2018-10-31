package io.github.ovso.righttoknow.ui.main.violation;

import android.os.Bundle;
import android.text.TextUtils;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.network.model.violation.Violation;
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

public class ViolationPresenterImpl implements ViolationPresenter {

  private ViolationPresenter.View view;
  private BaseAdapterDataModel<Violation> adapterDataModel;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private SchedulersFacade schedulersFacade;
  private ResourceProvider resourceProvider;

  ViolationPresenterImpl(ViolationPresenter.View view,
      SchedulersFacade $schedulersFacade, ResourceProvider $resourceProvider) {
    this.view = view;
    schedulersFacade = $schedulersFacade;
    resourceProvider = $resourceProvider;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
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
        .subscribe(new SingleObserver<List<Violation>>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(List<Violation> violations) {
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

  private DatabaseReference getRef() {
    return FirebaseDatabase.getInstance().getReference("violation").child("items");
  }

  private List<Violation> toList(Iterator<DataSnapshot> iterator) {
    List<Violation> violations = new ArrayList<>();
    while (iterator.hasNext()) {
      Violation violation = iterator.next().getValue(Violation.class);
      violations.add(violation);
    }
    return violations;
  }

  @Override public void setAdapterModel(BaseAdapterDataModel<Violation> adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onRecyclerItemClick(Violation violation) {
    String webLink = violation.link;
    String address = violation.address;
    if (webLink != null) {
      view.navigateToViolationDetail(webLink, address);
    } else {
      view.showMessage(R.string.error_server);
    }
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
        .subscribe(new SingleObserver<List<Violation>>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(List<Violation> violations) {
            Timber.d("filter size = " + violations.size());
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
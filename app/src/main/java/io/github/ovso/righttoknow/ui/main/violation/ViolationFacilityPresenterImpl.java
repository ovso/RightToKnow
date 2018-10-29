package io.github.ovso.righttoknow.ui.main.violation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.framework.utils.Constants;
import io.github.ovso.righttoknow.framework.utils.TimeoutMillis;
import io.github.ovso.righttoknow.ui.main.violation.model.VioFac;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
  private SchedulersFacade schedulersFacade;
  private DatabaseReference databaseReference =
      FirebaseDatabase.getInstance().getReference("violation");

  ViolationFacilityPresenterImpl(ViolationFacilityPresenter.View view,
      SchedulersFacade $schedulersFacade) {
    this.view = view;
    connectUrl = Constants.BASE_URL + Constants.FAC_LIST_PATH_QUERY;
    schedulersFacade = $schedulersFacade;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    //reqDatabase();
    req();
  }

  private void req() {

    view.showLoading();

    compositeDisposable.add(Maybe.fromCallable(
        () -> VioFac.convertToItems(
            Jsoup.connect(connectUrl).timeout(TimeoutMillis.JSOUP.getValue()).get()))
        .onErrorReturn(throwable -> new ArrayList<>())
        .subscribeOn(schedulersFacade.io())
        .observeOn(schedulersFacade.ui())
        .subscribe(items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
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
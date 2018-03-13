package io.github.ovso.righttoknow.violationfacility;

import android.os.Bundle;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.violationfacility.model.ViolationFacility;
import io.github.ovso.righttoknow.violationfacility.model.ViolationFacility2;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import org.jsoup.Jsoup;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationFacilityPresenterImpl implements ViolationFacilityPresenter {

  private ViolationFacilityPresenter.View view;
  private BaseAdapterDataModel<ViolationFacility2> adapterDataModel;
  private DatabaseReference databaseReference;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  ViolationFacilityPresenterImpl(ViolationFacilityPresenter.View view) {
    this.view = view;
    databaseReference = FirebaseDatabase.getInstance().getReference().child("child_vio_fac");
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    req();
  }

  private void req() {
    view.showLoading();
    compositeDisposable.add(Observable.fromCallable(() -> ViolationFacility2.convertToItems(
        Jsoup.connect(Constants.BASE_URL + Constants.FAC_LIST_PATH_QUERY).get()))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          Timber.d(throwable);
          view.hideLoading();
        }));
  }

  @Override public void setAdapterModel(BaseAdapterDataModel<ViolationFacility2> adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onRecyclerItemClick(ViolationFacility2 violationFacility) {
    view.navigateToViolationFacilityDetail(violationFacility);
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

    compositeDisposable.add(Observable.fromCallable(new Callable<List<ViolationFacility2>>() {
      @Override public List<ViolationFacility2> call() throws Exception {
        return ViolationFacility2.convertToItems(
            Jsoup.connect(Constants.BASE_URL + Constants.FAC_LIST_PATH_QUERY).get());
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          Timber.d(throwable);
          view.hideLoading();
        }));

    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .map(dataSnapshot -> {
          ArrayList<ViolationFacility> items =
              ViolationFacility.getSearchResultItems(ViolationFacility.convertToItems(dataSnapshot),
                  query);
          Comparator<ViolationFacility> comparator = (t1, t2) -> Integer.valueOf(t2.getReg_number())
              .compareTo(Integer.valueOf(t1.getReg_number()));
          Collections.sort(items, comparator);
          return items;
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(items -> {
          //adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          view.showMessage(R.string.error_server);
          view.hideLoading();
        }));
  }

  @Override public void onDetach() {
    compositeDisposable.clear();
    compositeDisposable.dispose();
  }
}
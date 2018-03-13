package io.github.ovso.righttoknow.violationfacility;

import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.violationfacility.model.VioFac;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import org.jsoup.Jsoup;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationFacilityPresenterImpl implements ViolationFacilityPresenter {

  private ViolationFacilityPresenter.View view;
  private BaseAdapterDataModel<VioFac> adapterDataModel;
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
    compositeDisposable.add(Observable.fromCallable(() -> VioFac.convertToItems(
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

  @Override public void setAdapterModel(BaseAdapterDataModel<VioFac> adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onRecyclerItemClick(VioFac vioFac) {
    view.navigateToViolationFacilityDetail(vioFac);
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

    /*
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
    */

  }

  @Override public void onDetach() {
    compositeDisposable.clear();
    compositeDisposable.dispose();
  }
}
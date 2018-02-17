package io.github.ovso.righttoknow.violator;

import android.os.Bundle;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.violator.vo.Violator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 8. 3
 */

public class ViolatorFragmentPresenterImpl implements ViolatorFragmentPresenter {
  private ViolatorFragmentPresenter.View view;
  private ViolatorAdapterDataModel adapterDataModel;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private DatabaseReference databaseReference =
      FirebaseDatabase.getInstance().getReference().child("child_violator");

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
    RxFirebaseDatabase.data(databaseReference)
        .map(dataSnapshot -> Violator.convertToItems(dataSnapshot))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<ArrayList<Violator>>() {
          @Override public void accept(ArrayList<Violator> items) throws Exception {
            adapterDataModel.addAll(items);
            view.refresh();
            view.hideLoading();
          }
        }, throwable -> {
          view.showMessage(R.string.error_server);
          view.hideLoading();
        });
  }

  @Override public void setAdapterModel(ViolatorAdapterDataModel adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onRecyclerItemClick(Violator violator) {
    view.navigateToViolatorDetail(violator);
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
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .map(dataSnapshot -> Violator.getSearchResultItems(Violator.convertToItems(dataSnapshot),
            query))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          view.showMessage(R.string.error_server);
          view.showLoading();
        }));
  }
}

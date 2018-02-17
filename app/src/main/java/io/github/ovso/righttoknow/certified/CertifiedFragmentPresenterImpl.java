package io.github.ovso.righttoknow.certified;

import android.os.Bundle;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.certified.model.ChildCertified;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 21
 */

public class CertifiedFragmentPresenterImpl implements CertifiedFragmentPresenter {

  private CertifiedFragmentPresenter.View view;
  private BaseAdapterDataModel<ChildCertified> adapterDataModel;
  private DatabaseReference databaseReference =
      FirebaseDatabase.getInstance().getReference().child("child_certified");
  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  CertifiedFragmentPresenterImpl(CertifiedFragmentPresenter.View view) {
    this.view = view;
  }

  @Override public void onActivityCreate(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    req();
  }

  private void req() {
    view.showLoading();
    adapterDataModel.clear();
    view.refresh();
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .map(new Function<DataSnapshot, List<ChildCertified>>() {
          @Override public List<ChildCertified> apply(DataSnapshot dataSnapshot) throws Exception {
            return ChildCertified.convertToItems(dataSnapshot);
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<ChildCertified>>() {
          @Override public void accept(List<ChildCertified> items) throws Exception {
            adapterDataModel.addAll(items);
            view.refresh();
            view.hideLoading();
          }
        }, throwable -> {
          view.hideLoading();
          view.showMessage(R.string.error_server);
        }));
  }

  @Override public void onRecyclerItemClick(ChildCertified certified) {
    view.navigateToPDFViewer(certified.getPdf_name());
  }

  @Override public void setAdapterModel(BaseAdapterDataModel<ChildCertified> adapter) {
    adapterDataModel = adapter;
  }

  @Override public void onDestroyView() {
    compositeDisposable.dispose();
    compositeDisposable.clear();
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    req();
  }
}

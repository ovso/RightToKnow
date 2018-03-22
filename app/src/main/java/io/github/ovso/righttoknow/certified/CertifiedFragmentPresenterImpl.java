package io.github.ovso.righttoknow.certified;

import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ovso.righttoknow.certified.model.ChildCertified;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.Callable;
import org.jsoup.Jsoup;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 8. 21
 */

public class CertifiedFragmentPresenterImpl implements CertifiedFragmentPresenter {

  private CertifiedFragmentPresenter.View view;
  private BaseAdapterDataModel<ChildCertified> adapterDataModel;
  private DatabaseReference databaseReference =
      FirebaseDatabase.getInstance().getReference().child("child_certified");
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private String connectUrl;

  CertifiedFragmentPresenterImpl(CertifiedFragmentPresenter.View view) {
    this.view = view;
    connectUrl = Constants.BASE_URL + Constants.CERTIFIED_LIST_PATH_QUERY;
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
    /*
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .map(dataSnapshot -> ChildCertified.convertToItems(dataSnapshot))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          view.hideLoading();
          view.showMessage(R.string.error_server);
        }));
    */
    Maybe.fromCallable(new Callable<List<ChildCertified>>() {
      @Override public List<ChildCertified> call() throws Exception {
        return ChildCertified.convertToItems(Jsoup.connect(connectUrl).get());
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<ChildCertified>>() {
          @Override public void accept(List<ChildCertified> childCertifieds) throws Exception {
            Timber.d(childCertifieds.toString());
            view.hideLoading();
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            Timber.d(throwable);
            view.hideLoading();
          }
        });

  }

  @Override public void onRecyclerItemClick(ChildCertified certified) {
    //view.navigateToPDFViewer(certified.getPdf_name());
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

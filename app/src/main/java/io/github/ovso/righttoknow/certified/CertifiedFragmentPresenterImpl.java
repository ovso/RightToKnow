package io.github.ovso.righttoknow.certified;

import android.os.Bundle;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.certified.model.ChildCertified;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.common.TimeoutMillis;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
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
    compositeDisposable.add(
        Maybe.fromCallable(() -> ChildCertified.convertToItems(
            Jsoup.connect(connectUrl).timeout(TimeoutMillis.JSOUP.getValue()).get()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(items -> {
              adapterDataModel.addAll(items);
              view.refresh();
              view.hideLoading();
            }, throwable -> {
              view.showMessage(R.string.error_server);
              view.hideLoading();
            }));
  }

  @DebugLog @Override public void onRecyclerItemClick(ChildCertified item) {
    view.showLoading();
    final String url = Constants.BASE_URL + item.getDownloadUrl();
    final File dirPath = MyApplication.getInstance().getFilesDir();
    final String fileName = "child.pdf";
    File file = new File(dirPath.toString() + "/" + fileName);
    if (file.exists()) {
      file.delete();
    }
    PRDownloader.download(url, dirPath.toString(), fileName)
        .build()
        .start(new OnDownloadListener() {
          @DebugLog @Override public void onDownloadComplete() {
            File file = new File(MyApplication.getInstance().getFilesDir() + "/" + fileName);
            Timber.d(file.toString());
            view.navigateToPDFViewer(file);
            view.hideLoading();
          }

          @DebugLog @Override public void onError(Error error) {
            view.showMessage(R.string.error_server);
            view.hideLoading();
          }
        });
  }

  @Override public void setAdapterModel(BaseAdapterDataModel<ChildCertified> adapter) {
    adapterDataModel = adapter;
  }

  @Override public void onDestroyView() {
    compositeDisposable.dispose();
    compositeDisposable.clear();
    PRDownloader.cancelAll();
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    req();
  }
}

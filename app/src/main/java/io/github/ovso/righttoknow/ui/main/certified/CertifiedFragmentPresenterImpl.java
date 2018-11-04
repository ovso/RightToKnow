package io.github.ovso.righttoknow.ui.main.certified;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.network.model.VioData;
import io.github.ovso.righttoknow.data.network.model.certified.Certified;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.reactivex.disposables.CompositeDisposable;
import java.io.File;
import java.util.List;
import timber.log.Timber;

public class CertifiedFragmentPresenterImpl implements CertifiedFragmentPresenter {

  private CertifiedFragmentPresenter.View view;
  private BaseAdapterDataModel<Certified> adapterDataModel;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private VioData vioData;

  CertifiedFragmentPresenterImpl(CertifiedFragmentPresenter.View view,
      BaseAdapterDataModel<Certified> $adapterDataModel, VioData $vioData) {
    this.view = view;
    adapterDataModel = $adapterDataModel;
    vioData = $vioData;
  }

  @Override public void onActivityCreate(Bundle savedInstanceState) {
    view.setListener();
    view.setupRecyclerView();
    updateAdapter(vioData.certified.items);
  }

  private void updateAdapter(List<Certified> $items) {
    adapterDataModel.clear();
    adapterDataModel.addAll($items);
    view.refresh();
    view.hideLoading();
  }

  @Override public void onRecyclerItemClick(Certified item) {
    view.showLoading();
    final String url = item.download_url;
    final File dirPath = App.getInstance().getFilesDir();
    final String fileName = "child.pdf";
    File file = new File(dirPath.toString() + "/" + fileName);
    if (file.exists()) {
      file.delete();
    }
    PRDownloader.download(url, dirPath.toString(), fileName)
        .build()
        .start(new OnDownloadListener() {
          @Override public void onDownloadComplete() {
            File file = new File(App.getInstance().getFilesDir() + "/" + fileName);
            Timber.d(file.toString());
            view.navigateToPDFViewer(file);
            view.hideLoading();
          }

          @Override public void onError(Error error) {
            view.showMessage(R.string.error_server);
            view.hideLoading();
          }
        });
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  private void clear() {
    compositeDisposable.clear();
    PRDownloader.cancelAll();
  }

  @Override public void onRefresh() {
    updateAdapter(vioData.certified.items);
  }
}

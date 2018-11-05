package io.github.ovso.righttoknow.ui.main.certified;

import android.arch.lifecycle.LifecycleObserver;
import android.os.Bundle;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.data.network.model.certified.Certified;
import java.io.File;

public interface CertifiedFragmentPresenter extends LifecycleObserver {

  void onActivityCreate(Bundle savedInstanceState);

  void onRecyclerItemClick(Certified certified);

  void onRefresh();

  interface View {

    void setupRecyclerView();

    void showLoading();

    void hideLoading();

    void refresh();

    void setListener();

    void navigateToPDFViewer(File file);

    void showMessage(@StringRes int resId);
  }
}

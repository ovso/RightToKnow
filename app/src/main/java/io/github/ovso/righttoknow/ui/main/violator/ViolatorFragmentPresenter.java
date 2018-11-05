package io.github.ovso.righttoknow.ui.main.violator;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.data.network.model.violators.Violator;

public interface ViolatorFragmentPresenter extends LifecycleObserver {

  void onActivityCreate(Bundle savedInstanceState);

  void onRecyclerItemClick(Violator violator);

  void onRefresh();

  void onSearchQuery(String query);

  void onOptionsItemSelected(@IdRes int itemId);

  interface View {

    void hideLoading();

    void showLoading();

    void refresh();

    void setupAdapter();

    void setupRecyclerView();

    void setListener();

    Context getActivity();

    void setSearchResultText(@StringRes int resId);

    void showMessage(@StringRes int resId);

    void navigateToViolatorDetail(Violator violator);
  }
}

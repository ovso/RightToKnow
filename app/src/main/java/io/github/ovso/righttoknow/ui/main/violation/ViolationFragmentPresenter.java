package io.github.ovso.righttoknow.ui.main.violation;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.data.network.model.violation.Violation;

public interface ViolationFragmentPresenter extends LifecycleObserver {

  void onActivityCreated(Bundle savedInstanceState);

  void onRecyclerItemClick(Violation violation);

  void onRefresh();

  void onSearchQuery(String query);

  void onOptionsItemSelected(@IdRes int itemId);

  interface View {

    void setupRecyclerView();

    void setupAdapter();

    void refresh();

    void showLoading();

    void hideLoading();

    void setListener();

    Context getActivity();

    void setSearchResultText(@StringRes int resId);

    void showMessage(@StringRes int error_server);

    void navigateToContents(Violation violation);
  }
}
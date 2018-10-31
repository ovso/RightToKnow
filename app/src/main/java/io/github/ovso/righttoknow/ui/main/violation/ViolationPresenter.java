package io.github.ovso.righttoknow.ui.main.violation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.data.network.model.violation.Violation;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;

public interface ViolationPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterModel(BaseAdapterDataModel<Violation> adapterDataModel);

  void onRecyclerItemClick(Violation violation);

  void onRefresh();

  void onSearchQuery(String query);

  void onOptionsItemSelected(@IdRes int itemId);

  void onDestroyView();

  interface View {

    void setRecyclerView();

    void setAdapter();

    void refresh();

    void showLoading();

    void hideLoading();

    void navigateToViolationDetail(String webLink, String address);

    void setListener();

    Context getActivity();

    void setSearchResultText(@StringRes int resId);

    void showMessage(@StringRes int error_server);
  }
}
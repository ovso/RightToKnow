package io.github.ovso.righttoknow.ui.main.violation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.ui.main.violation.model.VioFac;

/**
 * Created by jaeho on 2017. 8. 1
 */

public interface ViolationFacilityPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterModel(BaseAdapterDataModel<VioFac> adapterDataModel);

  void onRecyclerItemClick(VioFac vioFac);

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

    void navigateToViolationFacilityDetail(String webLink, String address);

    void setListener();

    Context getActivity();

    void setSearchResultText(@StringRes int resId);

    void showMessage(@StringRes int error_server);
  }
}
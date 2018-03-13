package io.github.ovso.righttoknow.violationfacility;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.violationfacility.model.ViolationFacility2;

/**
 * Created by jaeho on 2017. 8. 1
 */

public interface ViolationFacilityPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterModel(BaseAdapterDataModel<ViolationFacility2> adapterDataModel);

  void onRecyclerItemClick(ViolationFacility2 violationFacility);

  void onRefresh();

  void onSearchQuery(String query);

  void onDetach();

  interface View {

    void setRecyclerView();

    void setAdapter();

    void refresh();

    void showLoading();

    void hideLoading();

    void navigateToViolationFacilityDetail(ViolationFacility2 violationFacility);

    void setListener();

    Context getActivity();

    void setSearchResultText(@StringRes int resId);

    void showMessage(@StringRes int error_server);
  }
}
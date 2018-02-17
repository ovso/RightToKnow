package io.github.ovso.righttoknow.violationfacility;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.violationfacility.model.ViolationFacility;

/**
 * Created by jaeho on 2017. 8. 1
 */

public interface ViolationFacilityPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterModel(FacilityAdapterDataModel adapterDataModel);

  void onRecyclerItemClick(ViolationFacility violationFacility);

  void onRefresh();

  void onSearchQuery(String query);

  void onDetach();

  interface View {

    void setRecyclerView();

    void setAdapter();

    void refresh();

    void showLoading();

    void hideLoading();

    void navigateToViolationFacilityDetail(ViolationFacility violationFacility);

    void setListener();

    Context getActivity();

    void setSearchResultText(@StringRes int resId);

    void showMessage(@StringRes int error_server);
  }
}
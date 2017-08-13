package io.github.ovso.righttoknow.violationfacility;

import android.content.Context;
import android.os.Bundle;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;

/**
 * Created by jaeho on 2017. 8. 1
 */

public interface ViolationFacilityPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterModel(FacilityAdapterDataModel adapterDataModel);

  void onRecyclerItemClick(ViolationFacility violationFacility);

  void onDestroyView();

  void onRefresh();

  void onNearbyClick();

  interface View {

    void setRecyclerView();

    void setAdapter();

    void refresh();

    void showLoading();

    void hideLoading();

    void navigateToViolationFacilityDetail(String link);

    void setListener();

    Context getActivity();
  }
}
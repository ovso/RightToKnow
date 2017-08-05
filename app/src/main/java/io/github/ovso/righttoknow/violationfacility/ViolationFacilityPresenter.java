package io.github.ovso.righttoknow.violationfacility;

import android.os.Bundle;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;

/**
 * Created by jaeho on 2017. 8. 1
 */

public interface ViolationFacilityPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterModel(BaseAdapterDataModel adapterDataModel);

  void onRecyclerItemClick(ViolationFacility violationFacility);

  void ondestroyView();

  interface View {

    void setRecyclerView();

    void setAdapter();

    void refresh();

    void showLoading();

    void hideLoading();

    void navigateToViolationFacilityDetail(String link);

    void setListener();

    void showSnackbar(String msg);
  }
}
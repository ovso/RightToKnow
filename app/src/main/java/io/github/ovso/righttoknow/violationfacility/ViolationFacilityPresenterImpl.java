package io.github.ovso.righttoknow.violationfacility;

import android.os.Bundle;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationFacilityPresenterImpl implements ViolationFacilityPresenter {

  private ViolationFacilityPresenter.View view;
  private BaseAdapterDataModel adapterDataModel;
  private ViolationFacilityInteractor violationFacilityInteractor;

  ViolationFacilityPresenterImpl(ViolationFacilityPresenter.View view) {
    this.view = view;
    violationFacilityInteractor = new ViolationFacilityInteractor();
    violationFacilityInteractor.setOnViolationfacilityResultListener(new ViolationFacilityInteractor.OnViolationfacilityResultListener() {
      @Override public void onPre() {
        view.showLoading();
      }

      @Override public void onResult(List<ViolationFacility> violationFacilities) {
        adapterDataModel.addAll(violationFacilities);
        view.refresh();
      }

      @Override public void onPost() {
        view.hideLoading();
      }
    });
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setAdapter();
    view.setRecyclerView();
    violationFacilityInteractor.req(
        Constants.BASE_URL + Constants.VIOLATION_PATH + Constants.VIOLATION_PARAM);
  }

  @Override public void setAdapterModel(BaseAdapterDataModel adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }
}
package io.github.ovso.righttoknow.violationfacility;

import android.os.Bundle;
import android.support.annotation.IdRes;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationFacilityPresenterImpl implements ViolationFacilityPresenter {

  private ViolationFacilityPresenter.View view;
  private FacilityAdapterDataModel<ViolationFacility> adapterDataModel;
  private ViolationFacilityInteractor violationFacilityInteractor;

  ViolationFacilityPresenterImpl(ViolationFacilityPresenter.View view) {
    this.view = view;
    violationFacilityInteractor = new ViolationFacilityInteractor();
    violationFacilityInteractor.setOnViolationFacilityResultListener(
        new OnViolationResultListener<List<ViolationFacility>>() {
          @Override public void onPre() {
            view.showLoading();
          }

          @Override public void onResult(List<ViolationFacility> violationFacilities) {
            adapterDataModel.clear();
            adapterDataModel.add(new ViolationFacility());
            adapterDataModel.addAll(violationFacilities);
            view.refresh();
          }

          @Override public void onPost() {
            view.hideLoading();
          }
        });
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    violationFacilityInteractor.req();
  }

  @Override public void setAdapterModel(FacilityAdapterDataModel adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onRecyclerItemClick(ViolationFacility violationFacility) {
    view.navigateToViolationFacilityDetail(violationFacility.getLink());
  }

  @Override public void ondestroyView() {
    violationFacilityInteractor.cancel();
  }

  @Override public void onMenuSelected(@IdRes int id) {
    if (id == R.id.menu_facility_turn) {
      adapterDataModel.sortTurn();
    } else if (id == R.id.menu_facility_sido) {
      adapterDataModel.sortSido();
    } else if (id == R.id.menu_facility_type) {
      adapterDataModel.sortType();
    }
  }
}
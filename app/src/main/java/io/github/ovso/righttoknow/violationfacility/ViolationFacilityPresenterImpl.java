package io.github.ovso.righttoknow.violationfacility;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.IdRes;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationFacilityPresenterImpl implements ViolationFacilityPresenter {

  private ViolationFacilityPresenter.View view;
  private FacilityAdapterDataModel<ViolationFacility> adapterDataModel;
  private ViolationFacilityInteractor violationFacilityInteractor;
  private LocationInteractor locationInteractor;
  ViolationFacilityPresenterImpl(ViolationFacilityPresenter.View view) {
    this.view = view;
    locationInteractor = new LocationInteractor();
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
      adapterDataModel.remove(0);
      adapterDataModel.sortTurn();
      adapterDataModel.add(0, new ViolationFacility());
    } else if (id == R.id.menu_facility_sido) {
      adapterDataModel.remove(0);
      adapterDataModel.sortSido();
      adapterDataModel.add(0, new ViolationFacility());
    } else if (id == R.id.menu_facility_type) {
      adapterDataModel.remove(0);
      adapterDataModel.sortType();
      adapterDataModel.add(0, new ViolationFacility());
    }
    view.refresh();
  }

  @Override public void onMyLocationMenuSelected() {
    new TedPermission(MyApplication.getInstance()).setPermissionListener(permissionlistener)
        .setDeniedMessage(
            "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
        .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)
        .check();
  }

  private PermissionListener permissionlistener = new PermissionListener() {
    @Override public void onPermissionGranted() {
      locationInteractor.req();
    }

    @Override public void onPermissionDenied(ArrayList<String> deniedPermissions) {
    }
  };
}
package io.github.ovso.righttoknow.violationfacility;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.widget.Toast;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.common.LocationAware;
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
  private LocationAware locationAware;

  ViolationFacilityPresenterImpl(ViolationFacilityPresenter.View view) {
    this.view = view;
    violationFacilityInteractor = new ViolationFacilityInteractor();
    violationFacilityInteractor.setOnViolationFacilityResultListener(onViolationResultListener);
    locationAware = new LocationAware(view.getActivity());
    locationAware.setLocationListener((latitude, longitude, date) -> {
      Toast.makeText(MyApplication.getInstance(), "" + latitude + ", " + longitude,
          Toast.LENGTH_SHORT).show();
      locationAware.stop();
      Log.d("OJH", "latitude = " + latitude + ", longitude = " + longitude);
    });
    locationAware.setOnRequestCallbackListener(new LocationAware.OnRequestCallbackListener() {
      @DebugLog @Override public void onSettingsChangeUnavailable() {
        //locationAware.stop();

      }

      @DebugLog @Override public void onLocationSettingsSuccess() {
        //locationAware.stop();
      }
    });
  }

  OnViolationResultListener<List<ViolationFacility>> onViolationResultListener =
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
      };

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

  @Override public void onDestroyView() {
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
        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
        .check();
  }

  private PermissionListener permissionlistener = new PermissionListener() {
    @Override public void onPermissionGranted() {
      locationAware.start();
    }

    @Override public void onPermissionDenied(ArrayList<String> deniedPermissions) {
    }
  };
}
package io.github.ovso.righttoknow.violationfacility;

import android.location.Address;
import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
import io.github.ovso.righttoknow.main.LocationAware;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
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
    locationAware.setLocationListener(onLocationListener);
  }

  private LocationAware.OnLocationListener onLocationListener =
      new LocationAware.OnLocationListener() {
        @Override public void onLocationChanged(double latitude, double longitude, String date) {
          //view.hideLoading();
        }

        @Override public void onAddressChanged(Address address) {
          adapterDataModel.searchMyLocation(address.getLocality(), address.getSubLocality());
          //adapterDataModel.searchMyLocation("구구구", address.getSubLocality());
          view.refresh();
          int itemSize = adapterDataModel.getSize();
          if(itemSize < 2) {
            view.setSearchResultText(R.string.no_result);
          } else {
            view.setSearchResultText(R.string.empty);
          }
          view.hideLoading();
        }

        @Override public void onError(String error) {
          view.hideLoading();
        }
      };

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

        @Override public void onError() {
          view.setSearchResultText(R.string.please_retry);
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
    view.navigateToViolationFacilityDetail(violationFacility);
  }

  @Override public void onDestroyView() {
    violationFacilityInteractor.cancel();
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    view.setSearchResultText(R.string.empty);
    violationFacilityInteractor.req();
  }

  @Override public void onNearbyClick() {
    view.showLoading();
    locationAware.start();
  }
}
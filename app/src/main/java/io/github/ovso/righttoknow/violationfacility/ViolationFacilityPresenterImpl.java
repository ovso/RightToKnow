package io.github.ovso.righttoknow.violationfacility;

import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
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
  private Handler handler;

  ViolationFacilityPresenterImpl(ViolationFacilityPresenter.View view) {
    this.view = view;
    violationFacilityInteractor = new ViolationFacilityInteractor();
    violationFacilityInteractor.setOnViolationFacilityResultListener(onViolationResultListener);

    locationAware = new LocationAware(view.getActivity());
    locationAware.setLocationListener(onLocationListener);
    handler = new Handler();
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
          if (itemSize < 2) {
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

  OnChildResultListener<List<ViolationFacility>> onViolationResultListener =
      new OnChildResultListener<List<ViolationFacility>>() {
        @Override public void onPre() {
          view.showLoading();
        }

        @Override public void onResult(List<ViolationFacility> violationFacilities) {
          adapterDataModel.clear();
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

  @DebugLog @Override public void onDestroyView() {
    violationFacilityInteractor.cancel();
    handler.removeCallbacks(hideLoadingRunnable);
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

  private Runnable hideLoadingRunnable = new Runnable() {
    @Override public void run() {
      view.hideLoading();
    }
  };

  @Override public void onSearchQuery(String query) {
    view.showLoading();
    adapterDataModel.searchAllWords(query);
    view.refresh();
    handler.postDelayed(hideLoadingRunnable, 500);
  }
}
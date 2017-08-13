package io.github.ovso.righttoknow.violator;

import android.location.Address;
import android.os.Bundle;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
import io.github.ovso.righttoknow.main.LocationAware;
import io.github.ovso.righttoknow.violator.vo.Violator;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 3
 */

public class ViolatorFragmentPresenterImpl implements ViolatorFragmentPresenter {
  private ViolatorFragmentPresenter.View view;
  private ViolatorInteractor violatorInteractor;
  private ViolatorAdapterDataModel adapterDataModel;
  private LocationAware locationAware;

  ViolatorFragmentPresenterImpl(ViolatorFragmentPresenter.View view) {
    this.view = view;
    violatorInteractor = new ViolatorInteractor();
    violatorInteractor.setOnViolationFacilityResultListener(onViolationResultListener);

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
          view.refresh();
          view.hideLoading();
        }

        @Override public void onError(String error) {
          view.hideLoading();
        }
      };

  OnViolationResultListener<List<Violator>> onViolationResultListener =
      new OnViolationResultListener<List<Violator>>() {
        @Override public void onPre() {
          view.showLoading();
        }

        @Override public void onResult(List<Violator> violators) {
          adapterDataModel.clear();
          adapterDataModel.add(new Violator());
          adapterDataModel.addAll(violators);
          view.refresh();
        }

        @Override public void onPost() {
          view.hideLoading();
        }
      };

  @Override public void onActivityCreate(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    violatorInteractor.req();
  }

  @Override public void setAdapterModel(ViolatorAdapterDataModel adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onRecyclerItemClick(Violator violator) {
    view.navigateToViolatorDetail(violator.getLink());
  }

  @Override public void onDestroyView() {
    violatorInteractor.cancel();
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    violatorInteractor.req();
  }

  @Override public void onNearbyClick() {
    view.showLoading();
    locationAware.start();
  }
}

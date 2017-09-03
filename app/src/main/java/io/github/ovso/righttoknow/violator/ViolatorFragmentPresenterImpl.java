package io.github.ovso.righttoknow.violator;

import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
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
  private Handler handler;
  ViolatorFragmentPresenterImpl(ViolatorFragmentPresenter.View view) {
    this.view = view;
    violatorInteractor = new ViolatorInteractor();
    violatorInteractor.setOnViolationFacilityResultListener(onViolationResultListener);

    locationAware = new LocationAware(view.getActivity());
    locationAware.setLocationListener(onLocationListener);
    handler = new Handler();
  }

  private LocationAware.OnLocationListener onLocationListener =
      new LocationAware.OnLocationListener() {
        @Override public void onLocationChanged(double latitude, double longitude, String date) {

        }

        @Override public void onAddressChanged(Address address) {
          adapterDataModel.searchMyLocation(address.getLocality(), address.getSubLocality());
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

  OnChildResultListener<List<Violator>> onViolationResultListener =
      new OnChildResultListener<List<Violator>>() {
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

        @Override public void onError() {
          view.setSearchResultText(R.string.please_retry);
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
    view.navigateToViolatorDetail(violator);
  }

  @Override public void onDestroyView() {
    violatorInteractor.cancel();
    handler.removeCallbacks(hideLoadingRunnable);
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    view.setSearchResultText(R.string.empty);
    violatorInteractor.req();
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
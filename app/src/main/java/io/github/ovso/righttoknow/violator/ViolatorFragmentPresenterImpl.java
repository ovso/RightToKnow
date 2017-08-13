package io.github.ovso.righttoknow.violator;

import android.location.Address;
import android.os.Bundle;
import android.support.annotation.IdRes;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
import io.github.ovso.righttoknow.violator.vo.Violator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 3
 */

public class ViolatorFragmentPresenterImpl implements ViolatorFragmentPresenter {
  private ViolatorFragmentPresenter.View view;
  private ViolatorInteractor violatorInteractor;
  private ViolatorAdapterDataModel adapterDataModel;

  private List<Violator> violators = new ArrayList<>();
  ViolatorFragmentPresenterImpl(ViolatorFragmentPresenter.View view) {
    this.view = view;
    violatorInteractor = new ViolatorInteractor();
    violatorInteractor.setOnViolationFacilityResultListener(
        new OnViolationResultListener<List<Violator>>() {
          @Override public void onPre() {
            view.showLoading();
          }

          @Override public void onResult(List<Violator> violators) {
            adapterDataModel.clear();
            adapterDataModel.add(new Violator());
            adapterDataModel.addAll(violators);
            view.refresh();
            ViolatorFragmentPresenterImpl.this.violators = violators;
          }

          @Override public void onPost() {
            view.hideLoading();
          }
        });
  }

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

  @Override public void onMenuSelected(@IdRes int id, Address address) {
    if (id == R.id.option_menu_my_location) {
      adapterDataModel.searchMyLocation(address.getLocality(), address.getSubLocality());
      view.refresh();
    } else if (id == R.id.option_menu_back_again) {
      adapterDataModel.clear();
      adapterDataModel.add(new Violator());
      adapterDataModel.addAll(violators);
      view.refresh();
    }

  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    violatorInteractor.req();
  }
}

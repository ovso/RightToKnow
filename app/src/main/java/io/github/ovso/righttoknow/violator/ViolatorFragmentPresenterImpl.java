package io.github.ovso.righttoknow.violator;

import android.os.Bundle;
import android.support.annotation.IdRes;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
import io.github.ovso.righttoknow.violator.vo.Violator;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 3
 */

public class ViolatorFragmentPresenterImpl implements ViolatorFragmentPresenter {
  private ViolatorFragmentPresenter.View view;
  private ViolatorInteractor violatorInteractor;
  private ViolatorAdapterDataModel adapterDataModel;

  ViolatorFragmentPresenterImpl(ViolatorFragmentPresenter.View view) {
    this.view = view;
    violatorInteractor = new ViolatorInteractor();
    violatorInteractor.setOnViolationFacilityResultListener(
        new OnViolationResultListener<List<Violator>>() {
          @Override public void onPre() {
            view.showLoading();
          }

          @Override public void onResult(List<Violator> results) {
            adapterDataModel.add(new Violator());
            adapterDataModel.addAll(results);
            view.refresh();
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

  @Override public void onMenuSelected(@IdRes int id) {
    if (id == R.id.menu_violator_turn) {
      adapterDataModel.sortTurn();
    } else if (id == R.id.menu_violator_sido) {
      adapterDataModel.sortSido();
    } else if (id == R.id.menu_violator_history) {
      adapterDataModel.sortHistory();
    }
  }
}

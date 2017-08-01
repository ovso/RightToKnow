package io.github.ovso.righttoknow.violation;

import android.os.Bundle;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.common.Constants;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationPresenterImpl implements ViolationPresenter {

  private ViolationPresenter.View view;
  private BaseAdapterDataModel adapterDataModel;
  private ViolationInteractor violationInteractor;

  ViolationPresenterImpl(ViolationPresenter.View view) {
    this.view = view;
    violationInteractor = new ViolationInteractor();
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setAdapter();
    view.setRecyclerView();
    violationInteractor.req(
        Constants.BASE_URL + Constants.VIOLATION_PATH + Constants.VIOLATION_PARAM);
  }

  @Override public void setAdapterModel(BaseAdapterDataModel adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }
}
package io.github.ovso.righttoknow.violator;

import android.os.Bundle;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
import io.github.ovso.righttoknow.violator.vo.Violator;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 3
 */

public class ViolatorFragmentPresenterImpl implements ViolatorFragmentPresenter {
  private ViolatorFragmentPresenter.View view;
  private ViolatorInteractor violatorInteractor;
  private BaseAdapterDataModel adapterDataModel;

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
    view.setAdapter();
    view.setRecyclerView();
    String url = "http://info.childcare.go.kr/info/cfvp/VioltactorSlL.jsp?limit=100";
    violatorInteractor.req(url);
  }

  @Override public void setAdapterModel(BaseAdapterDataModel adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onRecyclerItemClick(Violator violator) {
    view.navigateToViolatorDetail(violator.getLink());
  }
}

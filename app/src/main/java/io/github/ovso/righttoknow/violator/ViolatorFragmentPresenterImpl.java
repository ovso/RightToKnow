package io.github.ovso.righttoknow.violator;

import android.os.Bundle;
import android.util.Log;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.listener.OnViolationFacilityResultListener;
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
        new OnViolationFacilityResultListener<List<Violator>>() {
          @Override public void onPre() {
            view.showLoading();
          }

          @Override public void onResult(List<Violator> results) {
            Log.d("","");
          }

          @Override public void onPost() {
            view.hideLoading();
          }
        });
  }

  @Override public void onActivityCreate(Bundle savedInstanceState) {
    String url = "http://info.childcare.go.kr/info/cfvp/VioltactorSlL.jsp?limit=100";
    violatorInteractor.req(url);
  }
}

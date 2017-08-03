package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.common.ObjectUtils;
import io.github.ovso.righttoknow.listener.OnViolationFacilityResultListener;
import io.github.ovso.righttoknow.vfacilitydetail.vo.VFacilityDetail;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 2
 */

public class VFacilityDetailPresenterImpl implements VFacilityDetailPresenter {
  private VFacilityDetailPresenter.View view;
  private VFacilityDetailInteractor vFacilityDetailInteractor;
  VFacilityDetailPresenterImpl(VFacilityDetailPresenter.View view) {
    this.view = view;
    vFacilityDetailInteractor = new VFacilityDetailInteractor();
    vFacilityDetailInteractor.setOnViolationFacilityResultListener(
        new OnViolationFacilityResultListener<List<VFacilityDetail>>() {
          @Override public void onPre() {
            view.showLoading();
          }

          @Override public void onResult(List<VFacilityDetail> result) {
            if(!ObjectUtils.isEmpty(result)) {
              view.showContents(VFacilityDetailInteractor.getResultParse(result.get(0)));
            } else {
              view.showNoContents();
            }

          }

          @Override public void onPost() {
            view.hideLoading();
          }
        });
  }
  @Override public void onCreate(Bundle savedInstanceState, Intent intent) {
    view.setSupportActionBar();
    if (intent.hasExtra("link")) {
      String link = intent.getStringExtra("link");
      link = Constants.BASE_URL + link;
      vFacilityDetailInteractor.req(link);
    }
  }
}
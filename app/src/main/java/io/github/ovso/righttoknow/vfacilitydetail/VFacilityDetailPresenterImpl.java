package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import io.github.ovso.righttoknow.violationfacility.ViolationFacilityInteractor;

/**
 * Created by jaeho on 2017. 8. 2
 */

public class VFacilityDetailPresenterImpl implements VFacilityDetailPresenter {
  private VFacilityDetailPresenter.View view;
  private ViolationFacilityInteractor violationFacilityInteractor;

  VFacilityDetailPresenterImpl(VFacilityDetailPresenter.View view) {
    this.view = view;
    violationFacilityInteractor = new ViolationFacilityInteractor();
    violationFacilityInteractor.setOnViolationfacilityResultListener(
        new ViolationFacilityInteractor.OnViolationfacilityResultListener() {
          @Override public void onPre() {
            view.showLoading();
          }

          @Override public void onResult(Object result) {

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
      Log.d("TAG", "link = " + link);
    }
  }
}
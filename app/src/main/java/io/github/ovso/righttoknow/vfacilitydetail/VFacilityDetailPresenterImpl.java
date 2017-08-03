package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.common.ObjectUtils;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
import io.github.ovso.righttoknow.vfacilitydetail.vo.VFacilityDetail;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 2
 */

public class VFacilityDetailPresenterImpl implements VFacilityDetailPresenter {
  private VFacilityDetailPresenter.View view;
  private VFacilityDetailInteractor vFacilityDetailInteractor;
  private ViolatorDetailInteractor violatorDetailInteractor;

  VFacilityDetailPresenterImpl(VFacilityDetailPresenter.View view) {
    this.view = view;
    vFacilityDetailInteractor = new VFacilityDetailInteractor();
    violatorDetailInteractor = new ViolatorDetailInteractor();
    vFacilityDetailInteractor.setOnViolationFacilityResultListener(onViolationResultListener);
    violatorDetailInteractor.setOnViolationFacilityResultListener(onViolationResultListener);
  }

  OnViolationResultListener onViolationResultListener =
      new OnViolationResultListener<List<VFacilityDetail>>() {
        @Override public void onPre() {
          view.showLoading();
        }

        @Override public void onResult(List<VFacilityDetail> result) {
          if (!ObjectUtils.isEmpty(result)) {
            if(TextUtils.isEmpty(result.get(0).getHistory())) {
              view.showContents(vFacilityDetailInteractor.getResultParse(result.get(0)));
            } else {
              view.showContents(violatorDetailInteractor.getResultParse(result.get(0)));
            }
          } else {
            view.showNoContents();
          }
        }

        @Override public void onPost() {
          view.hideLoading();
        }
      };

  @Override public void onCreate(Bundle savedInstanceState, Intent intent) {
    view.setSupportActionBar();
    if (intent.hasExtra("link")) {
      String link = intent.getStringExtra("link");
      if (intent.hasExtra("from")) {
        int from = intent.getIntExtra("from", R.layout.fragment_violation);
        if (from == R.layout.fragment_violation) {
          link = Constants.BASE_URL + link;
          vFacilityDetailInteractor.req(link);
        } else if (from == R.layout.fragment_violator) {
          link = Constants.BASE_URL + link;
          violatorDetailInteractor.req(link);
        } else {
          new RuntimeException("What the..");
        }
      }
    }
  }
}
package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
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
  private VFacilityDetailModel model;

  VFacilityDetailPresenterImpl(VFacilityDetailPresenter.View view) {
    this.view = view;
    vFacilityDetailInteractor = new VFacilityDetailInteractor();
    violatorDetailInteractor = new ViolatorDetailInteractor();
    vFacilityDetailInteractor.setOnViolationFacilityResultListener(onViolationResultListener);
    violatorDetailInteractor.setOnViolationFacilityResultListener(onViolationResultListener);
    model = new VFacilityDetailModel();
  }

  OnViolationResultListener onViolationResultListener =
      new OnViolationResultListener<List<VFacilityDetail>>() {
        @Override public void onPre() {
          view.showLoading();
        }

        @Override public void onResult(List<VFacilityDetail> result) {
          if (!ObjectUtils.isEmpty(result)) {
            if (model.getFrom() == R.layout.fragment_violation) {
              view.showContents(vFacilityDetailInteractor.getResultParse(result.get(0)));
            } else {
              view.showContents(violatorDetailInteractor.getResultParse(result.get(0)));
            }

            if (showSnackbar) {
              view.showSnackbar(MyApplication.getInstance().getString(R.string.msg_latest_info));
              showSnackbar = false;
            }
          }
        }

        @Override public void onPost() {
          view.hideLoading();
        }
      };

  @Override public void onCreate(Bundle savedInstanceState, Intent intent) {
    view.setListener();
    view.setSupportActionBar();

    if (intent.hasExtra("link")) {
      model.setIntent(intent);
      if (intent.hasExtra("from")) {
        if (model.getFrom() == R.layout.fragment_violation) {
          vFacilityDetailInteractor.req(model.getLink());
        } else if (model.getFrom() == R.layout.fragment_violator) {
          violatorDetailInteractor.req(model.getLink());
        } else {
          new RuntimeException("What the..");
        }
        view.setTitle(model.getTitle());
      }
    }
  }

  private boolean showSnackbar = false;

  @Override public void onRefresh() {
    showSnackbar = true;
    if (model.getFrom() == R.layout.fragment_violation) {
      vFacilityDetailInteractor.req(model.getLink());
    } else if (model.getFrom() == R.layout.fragment_violator) {
      violatorDetailInteractor.req(model.getLink());
    } else {
      new RuntimeException("What the...");
    }
  }

  @Override public void onDestroy() {
    if (model.getFrom() == R.layout.fragment_violation) {
      vFacilityDetailInteractor.cancel();
    } else {
      violatorDetailInteractor.cancel();
    }

  }

  @Override public void onBackPressed() {
  }
}
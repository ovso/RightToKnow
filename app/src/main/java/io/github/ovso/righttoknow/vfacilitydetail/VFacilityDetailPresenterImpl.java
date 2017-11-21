package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import io.github.ovso.righttoknow.app.MyApplication;
import java.io.Serializable;

/**
 * Created by jaeho on 2017. 8. 2
 */

public class VFacilityDetailPresenterImpl implements VFacilityDetailPresenter {
  private VFacilityDetailPresenter.View view;
  private VFacilityDetailModel model;

  VFacilityDetailPresenterImpl(VFacilityDetailPresenter.View view) {
    this.view = view;
    model = new VFacilityDetailModel(MyApplication.getInstance());
  }

  @Override public void onCreate(Bundle savedInstanceState, Intent intent) {
    view.setListener();
    view.setSupportActionBar();
    if (intent.hasExtra("contents")) {
      Serializable contents = intent.getSerializableExtra("contents");
      view.setTitle(model.getTitle(contents));
      view.showContents(model.getContents(contents));
    }

    view.showAd();
  }

  @Override public void onDestroy() {
  }

  @Override public void onBackPressed() {
  }
}
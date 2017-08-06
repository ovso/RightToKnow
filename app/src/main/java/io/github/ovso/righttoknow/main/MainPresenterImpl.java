package io.github.ovso.righttoknow.main;

import android.net.Uri;
import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.common.Constants;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class MainPresenterImpl implements MainPresenter {

  private MainPresenter.View view;
  private BaseAdapterDataModel adapterDataModel;

  MainPresenterImpl(MainPresenter.View view) {
    this.view = view;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    view.setTitle(MyApplication.getInstance().getString(R.string.title_vioation_facility_inquiry));
    view.setListener();
    view.showViolationFacilityFragment();
    view.setBottomNavigationView();
  }

  @Override public void onNavigationItemSelected(int id) {
    switch (id) {
      case R.id.nav_review:
        view.navigateToReview(Uri.parse(Constants.URL_REVIEW));
        break;
      case R.id.nav_share:
        view.navigateToShare(Constants.URL_SHARE);
        break;
    }
  }

  @Override public void onBottomNavigationItemSelected(int id) {
    switch (id) {
      case R.id.bottom_nav_violation_facility:
        view.showViolationFacilityFragment();
        break;
      case R.id.bottom_nav_violator:
        view.showViolatorFragment();
        break;
    }
  }
}
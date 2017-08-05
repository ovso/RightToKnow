package io.github.ovso.righttoknow.main;

import android.net.Uri;
import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.fragment.BaseFragment;
import io.github.ovso.righttoknow.violationfacility.ViolationFacilityFragment;
import io.github.ovso.righttoknow.violator.ViolatorFragment;
import java.util.ArrayList;
import java.util.List;

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
    view.setAdapter();
    view.setTabLayout();
    view.setViewPager();
    refreshAdapter();
  }

  private void refreshAdapter() {
    List<BaseFragment> fragments = new ArrayList<>();
    fragments.add(ViolationFacilityFragment.newInstance(null));
    fragments.add(ViolatorFragment.newInstance(null));
    adapterDataModel.addAll(fragments);
    view.refreshAdapter();
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
      case R.id.bottom_nav_violate:
        view.showViolateFragment();
        break;
      case R.id.bottom_nav_wrongdoer:
        view.showWrongdoerFragment();
        break;
    }
  }

  @Override public void setAdapterDataModel(BaseAdapterDataModel adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onPageChanged(int position) {
    switch (position) {
      case 0:
        view.setSelectedBottomNavigation(R.id.bottom_nav_violate);
        view.setTitle(MyApplication.getInstance().getString(R.string.day_care_center) + " " +
            MyApplication.getInstance().getString(R.string.title_vioation_facility_inquiry));
        break;
      case 1:
        view.setSelectedBottomNavigation(R.id.bottom_nav_wrongdoer);
        view.setTitle(MyApplication.getInstance().getString(R.string.day_care_center) + " " +
            MyApplication.getInstance().getString(R.string.title_violator_inquiry));
        break;
    }
  }
}
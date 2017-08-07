package io.github.ovso.righttoknow.main;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.common.Constants;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class MainPresenterImpl implements MainPresenter {

  private MainPresenter.View view;

  MainPresenterImpl(MainPresenter.View view) {
    this.view = view;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    view.setTitle(getTitle(Constants.ITEM_VIOLATION_FACILITY));
    view.setListener();
    view.setViewPager();
    view.setBottomNavigationViewBehavior();
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
        view.setViewPagerCurrentItem(Constants.ITEM_VIOLATION_FACILITY);
        break;
      case R.id.bottom_nav_violator:
        view.setViewPagerCurrentItem(Constants.ITEM_VIOLATOR);
        break;
    }
  }

  @Override public void onAdapterPageChanged(int position) {
    view.setCheckedBottomNavigationView(position);
    view.setViewPagerCurrentItem(position);
    view.setTitle(getTitle(position));
  }

  @Override public void onCreateOptionsMenu(int currentItem, Menu menu) {
    switch (currentItem) {
      case Constants.ITEM_VIOLATION_FACILITY:
        view.setOptionsMenu(R.menu.menu_main, menu);
        break;
      case Constants.ITEM_VIOLATOR:
        view.setOptionsMenu(R.menu.menu_main_violator, menu);
        break;
    }
    view.refreshOptionsMenu();
  }

  @Override public void onOptionsItemSelected(int itemId) {

  }

  private String getTitle(int position) {
    Resources res = MyApplication.getInstance().getResources();
    String title = res.getString(R.string.title_vioation_facility_inquiry);
    switch (position) {
      case Constants.ITEM_VIOLATION_FACILITY:
        title = res.getString(R.string.day_care_center) + " " + res.getString(
            R.string.title_vioation_facility_inquiry);
        break;
      case Constants.ITEM_VIOLATOR:
        title = res.getString(R.string.day_care_center) + " " + res.getString(
            R.string.title_violator_inquiry);
        break;
      default:

        break;
    }
    return title;
  }
}
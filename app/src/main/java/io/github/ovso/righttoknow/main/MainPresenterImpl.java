package io.github.ovso.righttoknow.main;

import android.Manifest;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.common.Utility;
import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class MainPresenterImpl implements MainPresenter {

  private MainPresenter.View view;
  private MainModel model;
  MainPresenterImpl(MainPresenter.View view) {
    this.view = view;
    model = new MainModel();
    view.changeTheme();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    view.setTitle(getTitle(Constants.ITEM_VIOLATION_FACILITY));
    view.setVersionName(
        MyApplication.getInstance().getString(R.string.version) + " " + Utility.getVersionName(
            MyApplication.getInstance()));
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
      case R.id.nav_open_source:
        view.showOpensourceLicenses(model.getNotices());
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
      case R.id.bottom_nav_certified:
        view.setViewPagerCurrentItem(Constants.ITEM_CERTIFIED);
    }
  }

  @Override public void onAdapterPageChanged(int position) {
    view.setCheckedBottomNavigationView(position);
    view.setViewPagerCurrentItem(position);
    view.setTitle(getTitle(position));
    view.invalidateOptionsMenu();
  }

  @Override public void onOptionsItemSelected(int itemId) {
    if (itemId == R.id.option_menu_my_location) {
      requestPermission();
    }
  }

  private void requestPermission() {
    new TedPermission(MyApplication.getInstance()).setPermissionListener(permissionlistener)
        .setDeniedMessage(R.string.location_permission_denied_message)
        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        .check();
  }

  private PermissionListener permissionlistener = new PermissionListener() {
    @Override public void onPermissionGranted() {
      view.onNearbyClick();
    }

    @Override public void onPermissionDenied(ArrayList<String> deniedPermissions) {
      //locationAware.stop();
    }
  };

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
      case Constants.ITEM_CERTIFIED:
        title = res.getString(R.string.title_certified) + " " + res.getString(
            R.string.day_care_center);
        break;
      default:

        break;
    }
    return title;
  }
}
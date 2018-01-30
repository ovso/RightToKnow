package io.github.ovso.righttoknow.main;

import android.Manifest;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.common.MessagingHandler;
import io.github.ovso.righttoknow.common.Utility;
import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 31
 */

class MainPresenterImpl implements MainPresenter {
  private MainPresenter.View view;

  MainPresenterImpl(MainPresenter.View view) {
    this.view = view;
    view.changeTheme();
  }

  @Override public void onNewIntent(Intent intent) {
    handlingForIntent(intent);
  }

  @Override public void onBackPressed(boolean isDrawerOpen) {
    if (isDrawerOpen) {
      view.closeDrawer();
    } else {
      view.finish();
    }
  }

  private void handlingForIntent(Intent intent) {
    if (intent.hasExtra(Constants.FCM_KEY_CONTENT_POSITION)) {
      view.setViewPagerCurrentItem(MessagingHandler.getContentPosition(intent.getExtras()));
    }
  }

  @Override public void onCreate(Bundle savedInstanceState, Intent intent) {
    view.setTitle(getTitle(Constants.ITEM_VIOLATION_FACILITY));
    view.setVersionName(
        MyApplication.getInstance().getString(R.string.version) + " " + Utility.getVersionName(
            MyApplication.getInstance()));
    view.setListener();
    //view.setBottomNavigationViewBehavior();
    view.setSearchView();
    view.showAd();

    handlingForIntent(intent);
  }

  @Override public void onNavigationItemSelected(int id) {
    switch (id) {
      case R.id.nav_child_abuse:
        view.navigateToChildAbuse();
        break;
      case R.id.nav_settings:
        view.navigateToSettings();
        break;
      case R.id.nav_help:
        String msg = MyApplication.getInstance().getString(R.string.source);
        view.showHelpAlert(msg);
        break;
    }
  }

  @Override public void onBottomNavigationItemSelected(int id) {
    switch (id) {
      case R.id.bottom_nav_violation_facility:
        view.showViolationFragment();
        break;
      case R.id.bottom_nav_violator:
        view.showViolatorFragment();
        break;
      case R.id.bottom_nav_certified:
        view.showCertifiedFragment();
        break;
      case R.id.bottom_nav_news:
        view.showNewsFragment();
        break;
      case R.id.bottom_nav_video:
        view.showVideoFragment();
        break;
    }
  }

  @Override public void onOptionsItemSelected(int itemId) {
    if (itemId == R.id.option_menu_my_location) {
      requestPermission();
    }
  }

  private void requestPermission() {
    TedPermission.with(MyApplication.getInstance())
        .setPermissionListener(permissionlistener)
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
        title = res.getString(R.string.title_vioation_facility_inquiry);
        break;
      case Constants.ITEM_VIOLATOR:
        title = res.getString(R.string.title_violator_inquiry);
        break;
      case Constants.ITEM_CERTIFIED:
        title =
            res.getString(R.string.title_certified) + " " + res.getString(R.string.day_care_center);
        break;
      case Constants.ITEM_NEWS:
        title = res.getString(R.string.title_news);
        break;
      case Constants.ITEM_VIDEO:
        title = res.getString(R.string.title_video);
        break;
      default:

        break;
    }
    return title;
  }
}
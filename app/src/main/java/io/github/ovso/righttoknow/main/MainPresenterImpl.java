package io.github.ovso.righttoknow.main;

import android.Manifest;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.common.Constants;
import java.util.ArrayList;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class MainPresenterImpl implements MainPresenter {

  private MainPresenter.View view;

  private LocationAware locationAware;
  MainPresenterImpl(MainPresenter.View view) {
    this.view = view;
  }
  @Override public void onCreate(Bundle savedInstanceState) {
    view.setTitle(getTitle(Constants.ITEM_VIOLATION_FACILITY));
    view.setListener();
    view.setViewPager();
    view.setBottomNavigationViewBehavior();

    locationAware = new LocationAware(view.getActivity());
    locationAware.setLocationListener((latitude, longitude, date) -> {
      locationAware.stop();
      Log.d("OJH", "latitude = " + latitude + ", longitude = " + longitude);
      
    });
    locationAware.setOnRequestCallbackListener(new LocationAware.OnRequestCallbackListener() {
      @DebugLog @Override public void onSettingsChangeUnavailable() {
        //locationAware.stop();

      }

      @DebugLog @Override public void onLocationSettingsSuccess() {
        //locationAware.stop();
      }
    });


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

  @Override public void onOptionsItemSelected(int itemId) {
    if(itemId == R.id.option_menu_my_location) {
      requestPermission();
    }
  }

  private void requestPermission() {
    new TedPermission(MyApplication.getInstance()).setPermissionListener(permissionlistener)
        .setDeniedMessage(
            "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
        .check();
  }
  private PermissionListener permissionlistener = new PermissionListener() {
    @Override public void onPermissionGranted() {
      locationAware.start();
    }

    @Override public void onPermissionDenied(ArrayList<String> deniedPermissions) {
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
      default:

        break;
    }
    return title;
  }
}
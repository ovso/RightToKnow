package io.github.ovso.righttoknow.ui.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MenuItem;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.listener.OnFragmentEventListener;
import io.github.ovso.righttoknow.framework.utils.Constants;
import io.github.ovso.righttoknow.framework.utils.Utility;
import io.github.ovso.righttoknow.utils.ResourceProvider;

public class MainPresenterImpl implements MainPresenter {
  private MainPresenter.View view;
  private ResourceProvider resourceProvider;

  MainPresenterImpl(MainPresenter.View view, ResourceProvider $resourceProvider) {
    this.view = view;
    resourceProvider = $resourceProvider;
  }

  @Override public void onNewIntent(Intent intent) {
    fcmNav(intent);
  }

  @Override public void onBackPressed(boolean isDrawerOpen) {
    if (isDrawerOpen) {
      view.closeDrawer();
    } else {
      view.finish();
    }
  }

  @Override public void onSubmit(@NonNull OnFragmentEventListener listener, String query) {
    if (!TextUtils.isEmpty(query)) {
      listener.onSearchQuery(query);
    } else {
      view.showMessage(R.string.empty_search_query);
    }
  }

  @Override public void onPrepareOptionsMenu(String simpleName, MenuItem item) {

  }

  private void fcmNav(Intent intent) {
    int position = intent.getIntExtra(Constants.FCM_KEY_CONTENT_POSITION, 0);
    switch (position) {
      case Constants.ITEM_VIOLATION_FACILITY:
        view.showViolationFragment();
        break;
      case Constants.ITEM_VIOLATOR:
        view.showViolatorFragment();
        break;
      case Constants.ITEM_CERTIFIED:
        view.showCertifiedFragment();
        break;
      case Constants.ITEM_NEWS:
        view.showNewsFragment();
        break;
      case Constants.ITEM_VIDEO:
        view.showVideoFragment();
        break;
    }
  }

  @Override public void onCreate(Intent intent) {
    String versionName =
        resourceProvider.getString(R.string.version) + " " + Utility.getVersionName(
            App.getInstance());
    view.setVersionName(versionName);
    view.setListener();
    view.setSearchView();

    fcmNav(intent);
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
        String msg = resourceProvider.getString(R.string.help_content);
        view.showHelpAlert(msg);
        break;
      case R.id.nav_share:
        view.navigateToShare();
        break;
    }
  }

  @Override public boolean onBottomNavigationItemSelected(int id) {
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

    return true;
  }
}
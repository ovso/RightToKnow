package io.github.ovso.righttoknow.main;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by jaeho on 2017. 7. 31
 */

public interface MainPresenter {

  void onCreate(Bundle savedInstanceState, Intent intent);

  void onNavigationItemSelected(int id);

  void onBottomNavigationItemSelected(int id);

  void onAdapterPageChanged(int position);

  void onOptionsItemSelected(int itemId);

  void onNewIntent(Intent intent);

  void onBackPressed(boolean isDrawerOpen);

  interface View {
    void setListener();

    void setTitle(String title);

    void setBottomNavigationViewBehavior();

    void setViewPager();

    void setCheckedBottomNavigationView(int position);

    void setViewPagerCurrentItem(int position);

    void hideLoading();

    void showLoading();

    void onNearbyClick();

    void setVersionName(String versionName);

    void invalidateOptionsMenu();

    void changeTheme();

    void closeSearchView();

    void setSearchView();

    void navigateToSettings();

    void closeDrawer();

    void finish();

    void showAd();

    void showHelpAlert(String msg);
  }
}

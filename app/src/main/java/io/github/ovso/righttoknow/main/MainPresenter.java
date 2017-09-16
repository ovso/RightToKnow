package io.github.ovso.righttoknow.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import de.psdev.licensesdialog.model.Notices;

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

  interface View {
    void setListener();

    void setTitle(String title);

    void navigateToStore(Uri uri);

    void navigateToShare(String url);

    void setBottomNavigationViewBehavior();

    void setViewPager();

    void setCheckedBottomNavigationView(int position);

    void setViewPagerCurrentItem(int position);

    Activity getActivity();

    void hideLoading();

    void showLoading();

    void onNearbyClick();

    void setVersionName(String versionName);

    void invalidateOptionsMenu();

    void showOpensourceLicenses(Notices notices);

    void changeTheme();

    void closeSearchView();

    void setSearchView();

    void navigateToSettings();

    void showAppUpdateDialog(String message);

    void showNoticeDialog(String message);
  }
}

package io.github.ovso.righttoknow.main;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.view.MenuItem;
import io.github.ovso.righttoknow.listener.OnFragmentEventListener;
import javax.annotation.Nonnull;

/**
 * Created by jaeho on 2017. 7. 31
 */

public interface MainPresenter {

  void onCreate(Intent intent);

  void onNavigationItemSelected(int id);

  void onBottomNavigationItemSelected(int id);

  void onNewIntent(Intent intent);

  void onBackPressed(boolean isDrawerOpen);

  void onSubmit(@Nonnull OnFragmentEventListener listener, String query);

  void onPrepareOptionsMenu(String simpleName, MenuItem item);

  interface View {
    void setListener();

    void setBottomNavigationViewBehavior();

    void setCheckedBottomNavigationView(int position);

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

    void navigateToChildAbuse();

    void showViolationFragment();

    void showViolatorFragment();

    void showCertifiedFragment();

    void showNewsFragment();

    void showVideoFragment();

    void showMessage(@StringRes int resId);

    void hideSearchView();

    void showSearchView();
  }
}

package io.github.ovso.righttoknow.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.view.Menu;

/**
 * Created by jaeho on 2017. 7. 31
 */

public interface MainPresenter {
  void onCreate(Bundle savedInstanceState);

  void onNavigationItemSelected(int id);

  void onBottomNavigationItemSelected(int id);

  void onAdapterPageChanged(int position);

  void onCreateOptionsMenu(int currentItem, Menu menu);

  interface View {
    void setListener();

    void setTitle(String title);

    void navigateToReview(Uri uri);

    void navigateToShare(String url);

    void setBottomNavigationViewBehavior();

    void setViewPager();

    void setCheckedBottomNavigationView(int position);

    void setViewPagerCurrentItem(int position);

    void setOptionsMenu(@MenuRes int menuRes, Menu menu);

    void refreshOptionsMenu();
  }
}

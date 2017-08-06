package io.github.ovso.righttoknow.main;

import android.net.Uri;
import android.os.Bundle;

/**
 * Created by jaeho on 2017. 7. 31
 */

public interface MainPresenter {
  void onCreate(Bundle savedInstanceState);

  void onNavigationItemSelected(int id);

  void onBottomNavigationItemSelected(int id);

  interface View {
    void setListener();

    void setSelectedBottomNavigation(int id);

    void setTitle(String title);

    void navigateToReview(Uri uri);

    void navigateToShare(String url);

    void showViolationFacilityFragment();

    void showViolatorFragment();

    void setBottomNavigationView();
  }
}

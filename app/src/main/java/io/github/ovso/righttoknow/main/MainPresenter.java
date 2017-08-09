package io.github.ovso.righttoknow.main;

import android.app.Activity;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;

/**
 * Created by jaeho on 2017. 7. 31
 */

public interface MainPresenter {
  void onCreate(Bundle savedInstanceState);

  void onNavigationItemSelected(int id);

  void onBottomNavigationItemSelected(int id);

  void onAdapterPageChanged(int position);

  void onOptionsItemSelected(int itemId);

  interface View {
    void setListener();

    void setTitle(String title);

    void navigateToReview(Uri uri);

    void navigateToShare(String url);

    void setBottomNavigationViewBehavior();

    void setViewPager();

    void setCheckedBottomNavigationView(int position);

    void setViewPagerCurrentItem(int position);

    Activity getActivity();

    void hideLoading();

    void showLoading();

    void postAddress(@IdRes int id,  Address address);
  }
}

package io.github.ovso.righttoknow.main;

import android.os.Bundle;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;

/**
 * Created by jaeho on 2017. 7. 31
 */

public interface MainPresenter {
  void onCreate(Bundle savedInstanceState);

  void onNavigationItemSelected(int id);

  void onBottomNavigationItemSelected(int id);

  void setAdapterDataModel(BaseAdapterDataModel adapterDataModel);

  void onPageChanged(int position);

  interface View {
    void setListener();

    void showViolateFragment();

    void showWrongdoerFragment();

    void setViewPager();

    void refreshAdapter();

    void setAdapter();

    void setSelectedBottomNavigation(int id);

    void setTitle(String title);
  }
}

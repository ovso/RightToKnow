package io.github.ovso.righttoknow.main;

import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.fragment.BaseFragment;
import io.github.ovso.righttoknow.violation.ViolationFragment;
import io.github.ovso.righttoknow.wrongdoer.WrongdoerFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class MainPresenterImpl implements MainPresenter {

  private MainPresenter.View view;
  private MainAdapterDataModel adapterDataModel;

  MainPresenterImpl(MainPresenter.View view) {
    this.view = view;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setViewPager();
    refreshAdapter();
  }

  private void refreshAdapter() {
    List<BaseFragment> fragments = new ArrayList<>();
    fragments.add(ViolationFragment.newInstance(null));
    fragments.add(WrongdoerFragment.newInstance(null));
    adapterDataModel.addAll(fragments);
    view.refreshAdapter();
  }

  @Override public void onNavigationItemSelected(int id) {
    switch (id) {
      case R.id.nav_violation:
        view.showViolateFragment();
        break;
      case R.id.nav_wrongdoer:
        view.showWrongdoerFragment();
        break;
    }
  }

  @Override public void onBottomNavigationItemSelected(int id) {
    switch (id) {
      case R.id.bottom_nav_violate:
        view.showViolateFragment();
        break;
      case R.id.bottom_nav_wrongdoer:
        view.showWrongdoerFragment();
        break;
    }
  }

  @Override public void setAdapterDataModel(MainAdapterDataModel adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onPageChanged(int position) {
    switch (position) {
      case 0:
        view.setSelectedBottomNavigation(R.id.bottom_nav_violate);
        break;
      case 1:
        view.setSelectedBottomNavigation(R.id.bottom_nav_wrongdoer);
        break;
    }
  }
}
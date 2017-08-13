package io.github.ovso.righttoknow.main;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.customview.BottomNavigationViewBehavior;
import io.github.ovso.righttoknow.fragment.BaseFragment;
import io.github.ovso.righttoknow.listener.OnFragmentEventListener;
import io.github.ovso.righttoknow.listener.OnSimplePageChangeListener;
import io.github.ovso.righttoknow.violationfacility.ViolationFacilityFragment;
import io.github.ovso.righttoknow.violator.ViolatorFragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainPresenter.View {

  private MainPresenter presenter;
  @BindView(R.id.drawer_layout) DrawerLayout drawer;
  @BindView(R.id.nav_view) NavigationView navigationView;
  @BindView(R.id.bottom_navigation_view) BottomNavigationView bottomNavigationView;
  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.progress_bar) ProgressBar progressBar;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = new MainPresenterImpl(this);
    presenter.onCreate(null);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    presenter.onOptionsItemSelected(item.getItemId());
    return true;
    //return super.onOptionsItemSelected(item);
  }

  @Override public int getLayoutResId() {
    return R.layout.activity_main;
  }

  @SuppressWarnings("deprecation") @Override public void setListener() {
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, getToolbar(), R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    navigationView.setNavigationItemSelectedListener(item -> {
      presenter.onNavigationItemSelected(item.getItemId());
      drawer.closeDrawer(GravityCompat.START);
      return true;
    });
    bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
      presenter.onBottomNavigationItemSelected(item.getItemId());
      return true;
    });
    viewPager.addOnPageChangeListener(new OnSimplePageChangeListener() {
      @Override public void onPageChanged(int position) {
        presenter.onAdapterPageChanged(position);
      }
    });
  }

  @Override public void setTitle(String title) {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(title);
    }
  }

  @Override public void navigateToReview(Uri uri) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(uri);
    startActivity(intent);
  }

  @Override public void navigateToShare(String url) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.addCategory(Intent.CATEGORY_DEFAULT);
    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
    intent.putExtra(Intent.EXTRA_TEXT, url);
    intent.setType("text/plain");

    intent = Intent.createChooser(intent, getString(R.string.app_share));
    startActivity(intent);
  }

  @Override public void setBottomNavigationViewBehavior() {
    try {
      CoordinatorLayout.LayoutParams layoutParams =
          (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
      layoutParams.setBehavior(new BottomNavigationViewBehavior());
    } catch (ClassCastException e) {
      e.printStackTrace();
    }
  }

  private OnFragmentEventListener onViolationFacilityFragListener;
  private OnFragmentEventListener onViolatorFragListener;

  @Override public void setViewPager() {
    ViolationFacilityFragment facilityFragment = ViolationFacilityFragment.newInstance(null);
    ViolatorFragment violatorFragment = ViolatorFragment.newInstance(null);
    onViolationFacilityFragListener = facilityFragment;
    onViolatorFragListener = violatorFragment;
    List<BaseFragment> fragments = new ArrayList<>();
    fragments.add(facilityFragment);
    fragments.add(violatorFragment);
    PagerBaseAdapter adapter = new PagerBaseAdapter(getSupportFragmentManager());
    adapter.addAll(fragments);
    viewPager.setAdapter(adapter);
  }

  @Override public void setCheckedBottomNavigationView(int position) {
    bottomNavigationView.getMenu().getItem(position).setChecked(true);
  }

  @Override public void setViewPagerCurrentItem(int position) {
    viewPager.setCurrentItem(position, true);
  }

  @Override public Activity getActivity() {
    return this;
  }

  @Override public void hideLoading() {
    progressBar.setVisibility(View.GONE);
  }

  @Override public void showLoading() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @DebugLog @Override public void postFragments(int id, Address address) {
    if (onViolationFacilityFragListener != null) {
      onViolationFacilityFragListener.onMenuSelected(id, address);
    }
    if (onViolatorFragListener != null) {
      onViolatorFragListener.onMenuSelected(id, address);
    }
  }

  @Override public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }
}
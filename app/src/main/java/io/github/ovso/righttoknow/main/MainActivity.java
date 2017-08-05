package io.github.ovso.righttoknow.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener, MainPresenter.View {

  private MainPresenter presenter;
  @BindView(R.id.drawer_layout) DrawerLayout drawer;
  @BindView(R.id.nav_view) NavigationView navigationView;
  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.tabLayout) TabLayout tabLayout;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = new MainPresenterImpl(this);
    presenter.onCreate(null);
  }

  @Override public int getLayoutResId() {
    return R.layout.activity_main;
  }

  @Override public void setListener() {
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, getToolbar(), R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override public void showViolateFragment() {
    viewPager.setCurrentItem(0, true);
  }

  @Override public void showWrongdoerFragment() {
    viewPager.setCurrentItem(1, true);
  }

  private BaseAdapterView baseAdapterView = null;

  @Override public void setViewPager() {
    viewPager.setAdapter(adapter);
    viewPager.addOnPageChangeListener(new OnSimplePageChangeListener() {
      @Override public void onPageChanged(int position) {
        presenter.onPageChanged(position);
      }
    });
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
  }

  @Override public void refreshAdapter() {
    baseAdapterView.refresh();
    viewPager.setAdapter(adapter);
  }

  private PagerBaseAdapter adapter;

  @Override public void setAdapter() {
    adapter = new PagerBaseAdapter(getSupportFragmentManager());
    presenter.setAdapterDataModel(adapter);
    baseAdapterView = adapter;
  }

  @Override public void setSelectedBottomNavigation(int id) {
  }

  @Override public void setTitle(String title) {
    getSupportActionBar().setTitle(title);
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

  @Override public void setTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText("어린이집 위반시설 조회"));
    tabLayout.addTab(tabLayout.newTab().setText("어린이집 위반행위자 조회"));
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  @Override public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @SuppressWarnings("StatementWithEmptyBody") @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    presenter.onNavigationItemSelected(item.getItemId());
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
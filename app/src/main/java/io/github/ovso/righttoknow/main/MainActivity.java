package io.github.ovso.righttoknow.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.certified.CertifiedFragment;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.customview.BottomNavigationViewBehavior;
import io.github.ovso.righttoknow.fragment.BaseFragment;
import io.github.ovso.righttoknow.listener.OnFragmentEventListener;
import io.github.ovso.righttoknow.listener.OnSimplePageChangeListener;
import io.github.ovso.righttoknow.news.NewsFragment;
import io.github.ovso.righttoknow.settings.SettingsActivity;
import io.github.ovso.righttoknow.video.VideoFragment;
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
  @BindView(R.id.ad_container) ViewGroup adContainer;

  @DebugLog @Override public void onCreate(Bundle savedInstanceState) {
    presenter = new MainPresenterImpl(this);
    super.onCreate(savedInstanceState);
    presenter.onCreate(savedInstanceState, getIntent());
  }

  @DebugLog @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    presenter.onNewIntent(intent);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuItem item;
    switch (viewPager.getCurrentItem()) {
      case 0:
      case 1:
        getMenuInflater().inflate(R.menu.main, menu);
        item = menu.findItem(R.id.option_menu_search);
        searchView.setMenuItem(item);
        break;
      default:
        break;
    }
    return true;
  }

  @DebugLog @Override public boolean onOptionsItemSelected(MenuItem item) {
    presenter.onOptionsItemSelected(item.getItemId());
    return false;
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
    fragments.add(CertifiedFragment.newInstance(null));
    fragments.add(NewsFragment.newInstance(null));
    fragments.add(VideoFragment.newInstance(null));

    PagerBaseAdapter adapter = new PagerBaseAdapter(getSupportFragmentManager());
    adapter.addAll(fragments);
    viewPager.setAdapter(adapter);
  }

  @Override public void setCheckedBottomNavigationView(int position) {
    bottomNavigationView.getMenu().getItem(position).setChecked(true);
  }

  @DebugLog @Override public void setViewPagerCurrentItem(int position) {
    viewPager.setCurrentItem(position, true);
  }

  @Override public void hideLoading() {

  }

  @Override public void showLoading() {

  }

  @Override public void onNearbyClick() {
    if (onViolationFacilityFragListener != null) {
      onViolationFacilityFragListener.onNearbyClick();
    }
    if (onViolatorFragListener != null) {
      onViolatorFragListener.onNearbyClick();
    }
  }

  @Override public void setVersionName(String versionName) {
    View view = navigationView.getHeaderView(0);
    TextView versionNameView = view.findViewById(R.id.version_name_textview);
    versionNameView.setText(versionName);
  }

  @Override public void changeTheme() {
    setTheme(R.style.AppTheme_NoActionBar);
  }

  @Override public void closeSearchView() {
    searchView.closeSearch();
  }

  @Override public void setSearchView() {
    searchView.setVoiceSearch(true);
    searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String query) {
        onViolationFacilityFragListener.onSearchQuery(query);
        onViolatorFragListener.onSearchQuery(query);
        return false;
      }

      @Override public boolean onQueryTextChange(String newText) {
        return false;
      }
    });

    searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
      @Override public void onSearchViewShown() {
        //Do some magic
      }

      @Override public void onSearchViewClosed() {
        //Do some magic
      }
    });
  }

  @Override public void navigateToSettings() {
    Intent intent = new Intent(this, SettingsActivity.class);
    startActivity(intent);
  }

  @Override public void onBackPressed() {
    presenter.onBackPressed(drawer.isDrawerOpen(GravityCompat.START));
  }

  @Override public void closeDrawer() {
    drawer.closeDrawer(GravityCompat.START);
  }

  @Override public void showAd() {
    CaulyAdView view;
    CaulyAdInfo info = new CaulyAdInfoBuilder(Constants.CAULY_APP_CODE).effect(
        CaulyAdInfo.Effect.Circle.toString()).build();
    view = new CaulyAdView(this);
    view.setAdInfo(info);
    view.setAdViewListener(new CaulyAdViewListener() {
      @DebugLog @Override public void onReceiveAd(CaulyAdView caulyAdView, boolean b) {

      }

      @DebugLog @Override
      public void onFailedToReceiveAd(CaulyAdView caulyAdView, int i, String s) {

      }

      @DebugLog @Override public void onShowLandingScreen(CaulyAdView caulyAdView) {

      }

      @DebugLog @Override public void onCloseLandingScreen(CaulyAdView caulyAdView) {

      }
    });

    ViewGroup adContainer = findViewById(R.id.ad_container);
    adContainer.addView(view);
  }

  @Override public void showHelpAlert(String msg) {
    new AlertDialog.Builder(this).setTitle(R.string.help).setMessage(msg).show();
  }
}
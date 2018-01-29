package io.github.ovso.righttoknow.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
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
import io.github.ovso.righttoknow.childabuse.ChildAbuseActivity;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.customview.BottomNavigationViewBehavior;
import io.github.ovso.righttoknow.news.NewsFragment;
import io.github.ovso.righttoknow.settings.SettingsActivity;
import io.github.ovso.righttoknow.video.VideoFragment;
import io.github.ovso.righttoknow.violationfacility.ViolationFacilityFragment;
import io.github.ovso.righttoknow.violator.ViolatorFragment;

public class MainActivity extends BaseActivity implements MainPresenter.View {

  private MainPresenter presenter;
  @BindView(R.id.drawer_layout) DrawerLayout drawer;
  @BindView(R.id.nav_view) NavigationView navigationView;
  @BindView(R.id.bottom_navigation_view) BottomNavigationView bottomNavigationView;
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
    getMenuInflater().inflate(R.menu.main, menu);
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

  @Override public void setCheckedBottomNavigationView(int position) {
    bottomNavigationView.getMenu().getItem(position).setChecked(true);
  }

  @DebugLog @Override public void setViewPagerCurrentItem(int position) {
  }

  @Override public void hideLoading() {

  }

  @Override public void showLoading() {

  }

  @Override public void onNearbyClick() {
    /*
    if (onViolationFacilityFragListener != null) {
      onViolationFacilityFragListener.onNearbyClick();
    }
    if (onViolatorFragListener != null) {
      onViolatorFragListener.onNearbyClick();
    }
    */
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
        /*
        onViolationFacilityFragListener.onSearchQuery(query);
        onViolatorFragListener.onSearchQuery(query);
        */
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

  @Override public void navigateToChildAbuse() {
    Intent intent = new Intent(this, ChildAbuseActivity.class);
    startActivity(intent);
  }

  @Override public void showViolationFragment() {
    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
            R.animator.enter_animation, R.animator.exit_animation)
        .replace(R.id.fragment_container, ViolationFacilityFragment.newInstance())
        .commit();
  }

  @Override public void showViolatorFragment() {
    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
            R.animator.enter_animation, R.animator.exit_animation)
        .replace(R.id.fragment_container, ViolatorFragment.newInstance())
        .commit();
  }

  @Override public void showCertifiedFragment() {
    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
            R.animator.enter_animation, R.animator.exit_animation)
        .replace(R.id.fragment_container, CertifiedFragment.newInstance())
        .commit();
  }

  @Override public void showNewsFragment() {
    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
            R.animator.enter_animation, R.animator.exit_animation)
        .replace(R.id.fragment_container, NewsFragment.newInstance())
        .commit();
  }

  @Override public void showVideoFragment() {
    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
            R.animator.enter_animation, R.animator.exit_animation)
        .replace(R.id.fragment_container, VideoFragment.newInstance())
        .commit();
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

    adContainer.addView(view);
  }

  @Override public void showHelpAlert(String msg) {
    new AlertDialog.Builder(this).setTitle(R.string.help).setMessage(msg).show();
  }
}
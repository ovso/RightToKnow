package io.github.ovso.righttoknow.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.certified.CertifiedFragment;
import io.github.ovso.righttoknow.childabuse.ChildAbuseActivity;
import io.github.ovso.righttoknow.framework.BaseActivity;
import io.github.ovso.righttoknow.framework.customview.BottomNavigationViewBehavior;
import io.github.ovso.righttoknow.framework.listener.OnFragmentEventListener;
import io.github.ovso.righttoknow.framework.listener.OnSimpleQueryTextListener;
import io.github.ovso.righttoknow.framework.utils.ObjectUtils;
import io.github.ovso.righttoknow.news.NewsFragment;
import io.github.ovso.righttoknow.settings.SettingsActivity;
import io.github.ovso.righttoknow.video.VideoFragment;
import io.github.ovso.righttoknow.violationfacility.ViolationFacilityFragment;
import io.github.ovso.righttoknow.violator.ViolatorFragment;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainPresenter.View {

  @Inject MainPresenter presenter;

  @BindView(R.id.drawer_layout) DrawerLayout drawer;
  @BindView(R.id.nav_view) NavigationView navigationView;
  @BindView(R.id.bottom_navigation_view) BottomNavigationView bottomNavigationView;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter.onCreate(getIntent());
  }

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    presenter.onNewIntent(intent);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    MenuItem item = menu.findItem(R.id.option_menu_search);
    searchView.setMenuItem(item);

    return true;
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
    bottomNavigationView.setOnNavigationItemSelectedListener(
        item -> presenter.onBottomNavigationItemSelected(item.getItemId()));
    bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
      // Do nothing..
    });
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
    searchView.setOnQueryTextListener(new OnSimpleQueryTextListener() {
      @Override public void onSubmit(String query) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (!ObjectUtils.isEmpty(f)) {
          presenter.onSubmit((OnFragmentEventListener) f, query);
        }
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

  @Override public void showMessage(int resId) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
  }

  @Override public void hideSearchView() {
    searchView.setVisibility(View.GONE);
  }

  @Override public void showSearchView() {
    searchView.setVisibility(View.VISIBLE);
  }

  @Override public void onBackPressed() {
    presenter.onBackPressed(drawer.isDrawerOpen(GravityCompat.START));
  }

  @Override public void closeDrawer() {
    drawer.closeDrawer(GravityCompat.START);
  }

  @Override public void showHelpAlert(String msg) {
    new AlertDialog.Builder(this).setTitle(R.string.help).setMessage(msg).show();
  }
}
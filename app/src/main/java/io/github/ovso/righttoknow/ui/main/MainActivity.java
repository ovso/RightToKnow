package io.github.ovso.righttoknow.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.BaseActivity;
import io.github.ovso.righttoknow.framework.customview.BottomNavigationViewBehavior;
import io.github.ovso.righttoknow.ui.childabuse.ChildAbuseActivity;
import io.github.ovso.righttoknow.ui.main.certified.CertifiedFragment;
import io.github.ovso.righttoknow.ui.main.news.NewsFragment;
import io.github.ovso.righttoknow.ui.main.video.VideoFragment;
import io.github.ovso.righttoknow.ui.main.violationfacility.ViolationFacilityFragment;
import io.github.ovso.righttoknow.ui.main.violator.ViolatorFragment;
import io.github.ovso.righttoknow.ui.settings.SettingsActivity;
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

    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            presenter.onNavigationItemSelected(item.getItemId());
            drawer.closeDrawer(GravityCompat.START);
            return true;
          }
        });
    bottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return presenter.onBottomNavigationItemSelected(item.getItemId());
          }
        });
    bottomNavigationView.setOnNavigationItemReselectedListener(
        new BottomNavigationView.OnNavigationItemReselectedListener() {
          @Override public void onNavigationItemReselected(@NonNull MenuItem item) {
            // Do nothing..
          }
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
  }

  @Override public void setSearchView() {
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
  }

  @Override public void showSearchView() {
  }

  @Override public void navigateToShare() {
    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
    intent.setType("text/plain");
    String subject = getString(R.string.app_name);
    String text = "https://play.google.com/store/apps/details?id=io.github.ovso.righttoknow";
    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    intent.putExtra(Intent.EXTRA_TEXT, text);
    Intent chooser = Intent.createChooser(intent, getString(R.string.share_message));
    startActivity(chooser);
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
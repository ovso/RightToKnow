package io.github.ovso.righttoknow.main;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import butterknife.BindView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.model.Notices;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.certified.CertifiedFragment;
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

  @Override public void onCreate(Bundle savedInstanceState) {
    presenter = new MainPresenterImpl(this);
    super.onCreate(savedInstanceState);
    presenter.onCreate(null);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    if (viewPager.getCurrentItem() < 2) {
      getMenuInflater().inflate(R.menu.main, menu);
      MenuItem item = menu.findItem(R.id.option_menu_search);
      searchView.setMenuItem(item);
    }
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    presenter.onOptionsItemSelected(item.getItemId());
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
    fragments.add(CertifiedFragment.newInstance(null));

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

  @Override public void showOpensourceLicenses(Notices notices) {
    new LicensesDialog.Builder(this).setNotices(notices).setIncludeOwnLicense(true).build().show();
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

  @Override public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }
}
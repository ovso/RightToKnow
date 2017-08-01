package io.github.ovso.righttoknow.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener, MainPresenter.View {

  private MainPresenter presenter;
  @BindView(R.id.drawer_layout) DrawerLayout drawer;
  @BindView(R.id.nav_view) NavigationView navigationView;
  @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
  @BindView(R.id.viewpager) ViewPager viewPager;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = new MainPresenterImpl(this);
    presenter.onCreate(null);

    final String source = "http://info.childcare.go.kr/info/cfvp/VioltfcltySlL.jsp";
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
    bottomNavigationView.setOnNavigationItemSelectedListener(
        onBottomNavigationItemSelectedListener);
  }

  @Override public void showViolateFragment() {
    viewPager.setCurrentItem(0, true);
  }

  @Override public void showWrongdoerFragment() {
    viewPager.setCurrentItem(1, true);
    /*
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fragment_container, WrongdoerFragment.newInstance(null));
    transaction.addToBackStack(null);
    transaction.commit();
    */
  }

  private MainAdapterView adapterView = null;

  @Override public void setViewPager() {
    viewPager.setAdapter(adapter);
    viewPager.addOnPageChangeListener(new OnSimplePageChangeListener() {
      @Override public void onPageChanged(int position) {
        presenter.onPageChanged(position);
      }
    });
  }

  @Override public void refreshAdapter() {
    adapterView.refresh();
    viewPager.setAdapter(adapter);
  }
  private MainPagerAdapter adapter;
  @Override public void setAdapter() {
    adapter = new MainPagerAdapter(getSupportFragmentManager());
    presenter.setAdapterDataModel(adapter);
    adapterView = adapter;
  }

  @Override public void setSelectedBottomNavigation(int id) {
    bottomNavigationView.setSelectedItemId(id);
  }

  public String TableToJson(String source) throws JSONException {

    Document doc;
    JSONObject rootJsonObject = new JSONObject();

    try {
      doc = Jsoup.connect(source).get();

      for (Element thead : doc.select("thead")) {
        Elements e = thead.select("tr").get(0).select("th");
        for (Element element : e) {
          String a = element.childNode(0).toString();
          Log.d("child", a);
        }
      }
      for (Element tbody : doc.select("tbody")) {

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return rootJsonObject.toString();
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

  private BottomNavigationView.OnNavigationItemSelectedListener
      onBottomNavigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          presenter.onBottomNavigationItemSelected(item.getItemId());
          return true;
        }
      };
}
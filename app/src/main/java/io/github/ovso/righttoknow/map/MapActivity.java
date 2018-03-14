package io.github.ovso.righttoknow.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import butterknife.BindView;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.main.BaseActivity;

/**
 * Created by jaeho on 2018. 3. 15
 */

public class MapActivity extends BaseActivity {
  private NMapContext mapContext;
  @BindView(R.id.map_view) NMapView mapView;

  @Override protected int getLayoutResId() {
    return R.layout.activity_map;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(R.string.vio_fac_loc);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mapContext = new NMapContext(this);
    mapContext.onCreate();
    mapView.setClientId(Security.NAVER_CLIENT_ID);
    mapContext.setupMapView(mapView);
    mapView.setClickable(true);
    mapView.setEnabled(true);
    mapView.setFocusable(true);
    mapView.setFocusableInTouchMode(true);
    mapView.requestFocus();
    mapView.displayZoomControls(true);
    mapView.setScalingFactor(4.0f);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onStart() {
    mapContext.onStart();
    super.onStart();
  }

  @Override protected void onResume() {
    mapContext.onResume();
    super.onResume();
  }

  @Override protected void onPause() {
    mapContext.onPause();
    super.onPause();
  }

  @Override protected void onStop() {
    mapContext.onStop();
    super.onStop();
  }

  @Override protected void onDestroy() {
    mapContext.onDestroy();
    super.onDestroy();
  }
}

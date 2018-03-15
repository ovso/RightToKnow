package io.github.ovso.righttoknow.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;
import butterknife.BindView;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.main.BaseActivity;
import timber.log.Timber;

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
    mapView.setScalingFactor(4f);

    setMarker();
  }

  private void setMarker() {

    double[] locations = getIntent().getDoubleArrayExtra("locations");
    Timber.d("locations = " + locations);

    NMapController mapController = mapView.getMapController();
    mapController.setZoomEnabled(true);
    mapController.setZoomToFixingPoint(true);

    NMapViewerResourceProvider provider = new NMapViewerResourceProvider(getApplicationContext());
    NMapOverlayManager mapOverlayManager =
        new NMapOverlayManager(getApplicationContext(), mapView, provider);
    NGeoPoint currentPoint = new NGeoPoint(locations[1], locations[0]);
    mapController.setMapCenter(currentPoint);
    NMapPOIdata poiData = new NMapPOIdata(1, provider);
    String facName = getIntent().getStringExtra("facName");
    Timber.d("facName = " + facName);
    if (TextUtils.isEmpty(facName)) {
      facName = getString(R.string.vio_fac);
    }
    poiData.addPOIitem(locations[1], locations[0], facName, NMapPOIflagType.PIN, 0);
    poiData.endPOIdata();

    NMapPOIdataOverlay poIdataOverlay = mapOverlayManager.createPOIdataOverlay(poiData, null);
    poIdataOverlay.showAllPOIdata(0);
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

package io.github.ovso.righttoknow.map;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;
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
import java.io.IOException;
import java.util.List;
import java.util.Locale;
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
    // seoul
    double lat = 37.5652894;
    double lng = 126.8494635;

    try {
      String address = getIntent().getStringExtra("address");
      Timber.d("address = " + address);
      List<Address> addressList =
          new Geocoder(getApplicationContext(), Locale.KOREA).getFromLocationName(address, 1);
      Timber.d("addressList = " + addressList);
      lat = addressList.get(0).getLatitude();
      lng = addressList.get(0).getLongitude();
    } catch (IOException e) {
      Timber.e(e);
      showMessage();
      return;
    } catch (Exception e) {
      Timber.e(e);
      showMessage();
      return;
    }

    NMapController mapController = mapView.getMapController();
    mapController.setZoomEnabled(true);
    mapController.setZoomToFixingPoint(true);
    NMapViewerResourceProvider provider = new NMapViewerResourceProvider(getApplicationContext());
    NMapOverlayManager mapOverlayManager =
        new NMapOverlayManager(getApplicationContext(), mapView, provider);
    NGeoPoint currentPoint = new NGeoPoint(lat, lng);
    mapController.setMapCenter(currentPoint);
    NMapPOIdata poiData = new NMapPOIdata(1, provider);
    String facName = getIntent().getStringExtra("facName");
    if (TextUtils.isEmpty(facName)) {
      facName = getString(R.string.vio_fac);
    }
    poiData.addPOIitem(lng, lat, facName, NMapPOIflagType.PIN, 0);
    poiData.endPOIdata();

    NMapPOIdataOverlay poIdataOverlay = mapOverlayManager.createPOIdataOverlay(poiData, null);
    poIdataOverlay.showAllPOIdata(0);
  }

  private void showMessage() {
    Toast.makeText(this, R.string.error_not_found_address, Toast.LENGTH_SHORT).show();
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

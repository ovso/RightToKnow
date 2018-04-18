package io.github.ovso.righttoknow.map;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.ViewGroup;
import butterknife.BindView;
import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.framework.BaseActivity;
import timber.log.Timber;

/**
 * Created by jaeho on 2018. 3. 15
 */

public class MapActivity extends BaseActivity implements OnMapReadyCallback {
  @BindView(R.id.ad_container) ViewGroup adContainer;
  @Override protected int getLayoutResId() {
    return R.layout.activity_map;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(R.string.vio_fac_loc);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    FragmentManager fragmentManager = getFragmentManager();
    MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map_fragment);
    mapFragment.getMapAsync(this);

    showAd();
  }

  private void showAd() {
    CaulyAdView view;
    CaulyAdInfo info =
        new CaulyAdInfoBuilder(Security.CAULY_APP_CODE.getValue()).effect(CaulyAdInfo.Effect.Circle.toString())
            .build();
    view = new CaulyAdView(this);
    view.setAdInfo(info);
    view.setAdViewListener(new CaulyAdViewListener() {
      @Override public void onReceiveAd(CaulyAdView caulyAdView, boolean b) {

      }

      @Override public void onFailedToReceiveAd(CaulyAdView caulyAdView, int i, String s) {

      }

      @Override public void onShowLandingScreen(CaulyAdView caulyAdView) {

      }

      @Override public void onCloseLandingScreen(CaulyAdView caulyAdView) {

      }
    });
    adContainer.addView(view);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return super.onOptionsItemSelected(item);
  }

  @Override public void onMapReady(GoogleMap googleMap) {
    double[] locations = getIntent().getDoubleArrayExtra("locations");
    Timber.d("locations = " + locations[0] + ", " + locations[1]);
    LatLng latlng = new LatLng(locations[0], locations[1]);
    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.anchor(0.0f, 1.0f);
    markerOptions.position(latlng);
    markerOptions.title(getIntent().getStringExtra("facName"));
    googleMap.addMarker(markerOptions);
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
  }
}

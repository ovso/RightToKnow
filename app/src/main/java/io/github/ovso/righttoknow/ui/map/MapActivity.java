package io.github.ovso.righttoknow.ui.map;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.BaseActivity;

/**
 * Created by jaeho on 2018. 3. 15
 */

public class MapActivity extends BaseActivity implements OnMapReadyCallback {
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
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return super.onOptionsItemSelected(item);
  }

  @Override public void onMapReady(GoogleMap googleMap) {
    double[] locations = getIntent().getDoubleArrayExtra("locations");
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

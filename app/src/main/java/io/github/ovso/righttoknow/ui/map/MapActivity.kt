package io.github.ovso.righttoknow.ui.map

import android.os.Bundle
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.databinding.ActivityMapBinding
import io.github.ovso.righttoknow.exts.viewBinding
import io.github.ovso.righttoknow.framework.BaseActivity

class MapActivity : BaseActivity(), OnMapReadyCallback {

  private val binding by viewBinding(ActivityMapBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    setTitle(R.string.vio_fac_loc)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
    mapFragment.getMapAsync(this)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    finish()
    return super.onOptionsItemSelected(item)
  }

  override fun onMapReady(googleMap: GoogleMap) {
    val locations = intent.getDoubleArrayExtra("locations")
    val latlng = LatLng(locations[0], locations[1])
    val markerOptions = MarkerOptions()
    markerOptions.anchor(0.0f, 1.0f)
    markerOptions.position(latlng)
    markerOptions.title(intent.getStringExtra("facName"))
    googleMap.addMarker(markerOptions)
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
  }
}
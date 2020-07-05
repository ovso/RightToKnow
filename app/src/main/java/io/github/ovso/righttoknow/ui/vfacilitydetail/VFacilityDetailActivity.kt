package io.github.ovso.righttoknow.ui.vfacilitydetail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.OnClick
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.databinding.ActivityVfacilitydetailBinding
import io.github.ovso.righttoknow.exts.viewBinding
import io.github.ovso.righttoknow.framework.AdsActivity
import io.github.ovso.righttoknow.ui.map.MapActivity
import javax.inject.Inject

class VFacilityDetailActivity : AdsActivity(), VFacilityDetailPresenter.View {
  @Inject
  lateinit var presenter: VFacilityDetailPresenter

  @JvmField
  @BindView(R.id.swipe_refresh)
  var swipe: SwipeRefreshLayout? = null

  @JvmField
  @BindView(R.id.share_button)
  var shareButton: Button? = null

  @JvmField
  @BindView(R.id.location_button)
  var locationButton: Button? = null

  private val binding by viewBinding(ActivityVfacilitydetailBinding::inflate)
  private val contentsTextView = binding.includeVfacilitydetailAppbarContainer.includeVfacilitydetailContentsContainer.contentsTextview

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    presenter.onCreate(savedInstanceState, intent)
  }

  override fun setTitle(titleId: Int) {
    super.setTitle(titleId)
  }

  override fun setSupportActionBar() {
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
  }

  override fun showContents(contents: String) {
    contentsTextView.text = contents
  }

  override fun setListener() {
    swipe!!.setColorSchemeResources(R.color.colorPrimary)
    swipe!!.setOnRefreshListener { presenter.onRefresh(this@VFacilityDetailActivity.intent) }
  }

  override fun showLoading() {
    swipe!!.isRefreshing = true
  }

  override fun hideLoading() {
    swipe!!.isRefreshing = false
  }

  override fun showMessage(resId: Int) {
    AlertDialog.Builder(this)
      .setMessage(resId)
      .setPositiveButton(
        android.R.string.ok) { dialogInterface: DialogInterface, which: Int -> dialogInterface.dismiss() }
      .show()
  }

  override fun navigateToMap(locations: DoubleArray, facName: String) {
    val intent = Intent(this, MapActivity::class.java)
    intent.putExtra("locations", locations)
    intent.putExtra("facName", facName)
    startActivity(intent)
  }

  override fun hideButton() {
    locationButton!!.visibility = View.INVISIBLE
    shareButton!!.visibility = View.INVISIBLE
  }

  override fun showButton() {
    locationButton!!.visibility = View.VISIBLE
    shareButton!!.visibility = View.VISIBLE
  }

  @OnClick(R.id.share_button)
  fun onShareClick() {
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_message))
    intent.putExtra(Intent.EXTRA_TEXT, contentsTextView.text.toString())
    val chooser = Intent.createChooser(intent, getString(R.string.share_message))
    startActivity(chooser)
  }

  @OnClick(R.id.location_button)
  fun onMapClick() {
    presenter.onMapClick(intent)
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

  override fun onResume() {
    super.onResume()
    if (intent.hasExtra("vio_fac_link")) {
      setTitle(R.string.title_vioation_facility_inquiry_detail)
    } else if (intent.hasExtra("violator_link")) {
      setTitle(R.string.title_violator_inquiry_detail)
    }
  }
}
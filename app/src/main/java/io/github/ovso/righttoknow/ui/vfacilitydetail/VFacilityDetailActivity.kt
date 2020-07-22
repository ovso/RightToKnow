package io.github.ovso.righttoknow.ui.vfacilitydetail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.databinding.ActivityVfacilitydetailBinding
import io.github.ovso.righttoknow.exts.viewBinding
import io.github.ovso.righttoknow.framework.AdsActivity
import io.github.ovso.righttoknow.ui.map.MapActivity
import kotlinx.android.synthetic.main.content_vfacilitydetail.*

class VFacilityDetailActivity : AdsActivity(), VFacilityDetailPresenter.View {
  private val presenter by lazy { VFacilityDetailPresenterImpl(this) }

  private val binding by viewBinding(ActivityVfacilitydetailBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setSupportActionBar(toolbar)
    setTitle(R.string.title_vioation_facility_inquiry_detail)
    presenter.onCreate(savedInstanceState, intent)
    showAd()

    btn_vfac_share.setOnClickListener {
      val intent = Intent()
      intent.action = Intent.ACTION_SEND
      intent.type = "text/plain"
      intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_message))
      intent.putExtra(Intent.EXTRA_TEXT, contents_textview.text.toString())
      val chooser = Intent.createChooser(intent, getString(R.string.share_message))
      startActivity(chooser)
    }
    btn_vfac_location.setOnClickListener {
      presenter.onMapClick(intent)
    }
  }

  override fun setSupportActionBar() {
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
  }

  override fun showContents(contents: String) {
    contents_textview.text = contents
  }

  override fun setListener() {
    srl_vfac.setColorSchemeResources(R.color.colorPrimary)
    srl_vfac.setOnRefreshListener { presenter.onRefresh(this@VFacilityDetailActivity.intent) }
  }

  override fun showLoading() {
    srl_vfac.isRefreshing = true
  }

  override fun hideLoading() {
    srl_vfac.isRefreshing = false
  }

  override fun showMessage(resId: Int) {
    AlertDialog.Builder(this)
      .setMessage(resId)
      .setPositiveButton(
        android.R.string.ok
      ) { dialogInterface: DialogInterface, which: Int -> dialogInterface.dismiss() }
      .show()
  }

  override fun navigateToMap(locations: DoubleArray, facName: String) {
    val intent = Intent(this, MapActivity::class.java)
    intent.putExtra("locations", locations)
    intent.putExtra("facName", facName)
    startActivity(intent)
  }

  override fun hideButton() {
    btn_vfac_location.visibility = View.INVISIBLE
    btn_vfac_share.visibility = View.INVISIBLE
  }

  override fun showButton() {
    btn_vfac_location.visibility = View.VISIBLE
    btn_vfac_share.visibility = View.VISIBLE
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

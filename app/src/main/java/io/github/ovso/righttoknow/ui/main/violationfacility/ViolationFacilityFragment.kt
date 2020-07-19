package io.github.ovso.righttoknow.ui.main.violationfacility

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.exts.launchActivity
import io.github.ovso.righttoknow.framework.BaseFragment
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener
import io.github.ovso.righttoknow.framework.listener.OnFragmentEventListener
import io.github.ovso.righttoknow.ui.main.violationfacility.model.VioFac
import io.github.ovso.righttoknow.ui.vfacilitydetail.VFacilityDetailActivity
import kotlinx.android.synthetic.main.fragment_violation.*

class ViolationFacilityFragment : BaseFragment(), ViolationFacilityPresenter.View,
  OnFragmentEventListener {

  private val adapter = ViolationFacilityAdapter()
  private var adapterView: BaseAdapterView? = null
  private lateinit var presenter: ViolationFacilityPresenter
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setHasOptionsMenu(true)
    presenter = ViolationFacilityPresenterImpl(this)
    presenter.onActivityCreated(savedInstanceState)
  }

  override fun getLayoutResId(): Int {
    return R.layout.fragment_violation
  }

  override fun setRecyclerView() {
    recyclerview.adapter = adapter
  }

  override fun setAdapter() {
    presenter.setAdapterModel(adapter)
    adapterView = adapter
    adapter.onRecyclerItemClickListener = object : OnRecyclerItemClickListener<VioFac> {
      override fun onItemClick(item: VioFac) {
        presenter.onRecyclerItemClick(item)
      }
    }
  }

  override fun refresh() {
    adapterView!!.refresh()
  }

  override fun showLoading() {
    swipe_refresh!!.isRefreshing = true
  }

  override fun hideLoading() {
    swipe_refresh!!.isRefreshing = false
  }

  override fun navigateToViolationFacilityDetail(
    webLink: String,
    address: String
  ) {
    launchActivity<VFacilityDetailActivity> {
      putExtra("vio_fac_link", webLink)
      putExtra("address", address)
    }
  }

  override fun setListener() {
    swipe_refresh!!.setOnRefreshListener { presenter.onRefresh() }
    swipe_refresh!!.setColorSchemeResources(R.color.colorPrimary)
    setHasOptionsMenu(true)
  }

  override fun setSearchResultText(resId: Int) {
    search_result_textview!!.setText(resId)
  }

  override fun showMessage(resId: Int) {
    Snackbar.make(container_view, resId, Snackbar.LENGTH_SHORT).show()
  }

  override fun onDestroyView() {
    presenter.onDestroyView()
    super.onDestroyView()
  }

  override fun onSearchQuery(query: String) {
    presenter.onSearchQuery(query)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    presenter.onOptionsItemSelected(item.itemId)
    return super.onOptionsItemSelected(item)
  }

  override fun onResume() {
    super.onResume()
    if (activity != null) {
      requireActivity().setTitle(R.string.title_vioation_facility_inquiry)
    }
  }

  companion object {
    fun newInstance(): ViolationFacilityFragment {
      return ViolationFacilityFragment()
    }
  }
}

package io.github.ovso.righttoknow.ui.main.violationfacility

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel
import io.github.ovso.righttoknow.ui.main.violationfacility.model.VioFac

interface ViolationFacilityPresenter {
  fun onActivityCreated(savedInstanceState: Bundle?)
  fun setAdapterModel(adapterDataModel: BaseAdapterDataModel<VioFac>)
  fun onRecyclerItemClick(vioFac: VioFac)
  fun onRefresh()
  fun onSearchQuery(query: String)
  fun onOptionsItemSelected(@IdRes itemId: Int)
  fun onDestroyView()
  interface View {
    fun setRecyclerView()
    fun setAdapter()
    fun refresh()
    fun showLoading()
    fun hideLoading()
    fun navigateToViolationFacilityDetail(
      webLink: String?,
      address: String?
    )

    fun setListener()
    fun setSearchResultText(@StringRes resId: Int)
    fun showMessage(@StringRes error_server: Int)
  }
}
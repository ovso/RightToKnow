package io.github.ovso.righttoknow.ui.main.violator

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import io.github.ovso.righttoknow.ui.main.violator.model.Violator

interface ViolatorFragmentPresenter {
  fun onActivityCreate(savedInstanceState: Bundle?)
  fun setAdapterModel(adapterDataModel: ViolatorAdapterDataModel<Violator>)
  fun onRecyclerItemClick(violator: Violator)
  fun onDestroyView()
  fun onRefresh()
  fun onSearchQuery(query: String)
  fun onOptionsItemSelected(@IdRes itemId: Int)
  interface View {
    fun hideLoading()
    fun showLoading()
    fun refresh()
    fun setAdapter()
    fun setRecyclerView()
    fun navigateToViolatorDetail(link: String?, address: String?)
    fun setListener()
    fun setSearchResultText(@StringRes resId: Int)
    fun showMessage(@StringRes resId: Int)
  }
}
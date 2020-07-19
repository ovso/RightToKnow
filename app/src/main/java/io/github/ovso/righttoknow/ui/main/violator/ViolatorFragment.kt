package io.github.ovso.righttoknow.ui.main.violator

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.BaseFragment
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener
import io.github.ovso.righttoknow.framework.listener.OnFragmentEventListener
import io.github.ovso.righttoknow.ui.main.violator.model.Violator
import io.github.ovso.righttoknow.ui.vfacilitydetail.VFacilityDetailActivity
import kotlinx.android.synthetic.main.fragment_violator.*

class ViolatorFragment : BaseFragment(),
  ViolatorFragmentPresenter.View,
  OnFragmentEventListener {
  private val adapter = ViolatorAdapter()
  private lateinit var adapterView: BaseAdapterView
  private lateinit var presenter: ViolatorFragmentPresenter
  override fun getLayoutResId(): Int {
    return R.layout.fragment_violator
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setHasOptionsMenu(true)
    requireActivity().setTitle(R.string.title_violator_inquiry)
    presenter = ViolatorFragmentPresenterImpl(this)
    presenter.onActivityCreate(savedInstanceState)
  }

  override fun hideLoading() {
    swipe_refresh.isRefreshing = false
  }

  override fun showLoading() {
    swipe_refresh.isRefreshing = true
  }

  override fun refresh() {
    adapterView.refresh()
  }

  override fun setAdapter() {
    presenter.setAdapterModel(adapter)
    adapterView = adapter
    adapter.onRecyclerItemClickListener = object : OnRecyclerItemClickListener<Violator> {
      override fun onItemClick(item: Violator) {
        presenter.onRecyclerItemClick(item)
      }
    }
  }

  override fun setRecyclerView() {
    val layoutManager = LinearLayoutManager(context)
    recyclerview.layoutManager = layoutManager
    recyclerview.adapter = adapter
  }

  override fun navigateToViolatorDetail(
    link: String?,
    address: String?
  ) {
    val intent = Intent(context, VFacilityDetailActivity::class.java)
    intent.putExtra("violator_link", link)
    intent.putExtra("address", address)
    startActivity(intent)
  }

  override fun setListener() {
    swipe_refresh.setOnRefreshListener { presenter.onRefresh() }
    swipe_refresh.setColorSchemeResources(R.color.colorPrimary)
    setHasOptionsMenu(true)
  }

  override fun setSearchResultText(@StringRes resId: Int) {
    search_result_textview.setText(resId)
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
    return false
  }

  override fun onCreateOptionsMenu(
    menu: Menu,
    inflater: MenuInflater
  ) {
    super.onCreateOptionsMenu(menu, inflater)
  }

  companion object {
    fun newInstance(): ViolatorFragment {
      return ViolatorFragment()
    }
  }
}
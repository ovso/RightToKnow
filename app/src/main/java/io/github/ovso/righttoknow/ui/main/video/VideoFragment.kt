package io.github.ovso.righttoknow.ui.main.video

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.data.network.VideoRequest
import io.github.ovso.righttoknow.data.network.model.video.SearchItem
import io.github.ovso.righttoknow.exts.launchActivity
import io.github.ovso.righttoknow.framework.BaseFragment
import io.github.ovso.righttoknow.framework.ad.MyAdView
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView
import io.github.ovso.righttoknow.ui.base.OnEndlessRecyclerScrollListener
import io.github.ovso.righttoknow.ui.base.OnEndlessRecyclerScrollListener.OnLoadMoreListener
import io.github.ovso.righttoknow.ui.base.OnRecyclerViewItemClickListener
import io.github.ovso.righttoknow.ui.video.LandscapeVideoActivity
import io.github.ovso.righttoknow.ui.video.PortraitVideoActivity
import io.github.ovso.righttoknow.utils.ResourceProvider
import io.github.ovso.righttoknow.utils.SchedulersFacade
import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragment : BaseFragment(), VideoFragmentPresenter.View, OnLoadMoreListener,
  OnRecyclerViewItemClickListener<SearchItem> {
  private lateinit var presenter: VideoFragmentPresenter
  private var adapterView: BaseAdapterView? = null
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setHasOptionsMenu(true)
    presenter = VideoFragmentPresenterImpl(
      this,
      MyAdView.getInterstitalAd(requireContext()),
      VideoRequest(),
      ResourceProvider(requireContext()),
      SchedulersFacade
    )

    presenter.onActivityCreated()
  }

  override fun getLayoutResId(): Int {
    return R.layout.fragment_video
  }

  override fun setRecyclerView() {
    val layout = LinearLayoutManager(context)
    val adapter = VideoAdapter()
    presenter.setAdapterDataModel(adapter)
    adapterView = adapter
    rv_video.layoutManager = layout
    rv_video.adapter = adapter
    rv_video.addOnScrollListener(
      OnEndlessRecyclerScrollListener.Builder(rv_video.layoutManager as LinearLayoutManager, this)
        .setVisibleThreshold(20)
        .build()
    )
    rv_video.setOnItemClickListener(this)
  }

  override fun refresh() {
    adapterView!!.refresh()
  }

  override fun setRefreshLayout() {
    srl_video.setOnRefreshListener { presenter.onRefresh() }
    srl_video.setColorSchemeResources(R.color.colorPrimary)
  }

  override fun navigateToPlayer(videoId: String) {
    launchActivity<PortraitVideoActivity> {
      putExtra("video_id", videoId)
    }
  }

  override fun showLoading() {
    srl_video.isRefreshing = true
  }

  override fun hideLoading() {
    srl_video.isRefreshing = false
  }

  override fun showMessage(resId: Int) {
    Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
  }

  override fun setLoaded() {
    rv_video.onEndlessRecyclerScrollListener?.setLoaded()
  }

  override fun onDestroyView() {
    presenter.onDestroyView()
    super.onDestroyView()
  }

  override fun onResume() {
    super.onResume()
    activity!!.setTitle(R.string.title_video)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    presenter.onActivityResult(requestCode, resultCode, data)
  }

  override fun onLoadMore() {
    presenter.onLoadMore()
  }

  override fun onItemClick(view: View, data: SearchItem, itemPosition: Int) {
    presenter.onItemClick(data)
  }

  override fun showVideoTypeDialog(onClickListener: DialogInterface.OnClickListener) {
    AlertDialog.Builder(context).setMessage(
      R.string.please_select_the_player_mode
    )
      .setPositiveButton(
        R.string.portrait_mode,
        onClickListener
      )
      .setNeutralButton(R.string.landscape_mode, onClickListener)
      .setNegativeButton(android.R.string.cancel, onClickListener)
      .show()
  }

  override fun showPortraitVideo(videoId: String) {
    val intent = Intent(context, PortraitVideoActivity::class.java)
    intent.putExtra("video_id", videoId)
    startActivity(intent)
  }

  override fun showLandscapeVideo(videoId: String) {
    val intent = Intent(context, LandscapeVideoActivity::class.java)
    intent.putExtra("video_id", videoId)
    startActivity(intent)
  }

  override fun showYoutubeUseWarningDialog() {
    AlertDialog.Builder(activity).setMessage(R.string.youtube_use_warning)
      .setPositiveButton(android.R.string.ok, null)
      .show()
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    menu.findItem(R.id.option_menu_sort).isVisible = false
    super.onCreateOptionsMenu(menu, inflater)
  }

  companion object {
    fun newInstance(): VideoFragment {
      return VideoFragment()
    }
  }
}

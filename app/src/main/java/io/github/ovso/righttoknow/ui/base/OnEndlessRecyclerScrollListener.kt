package io.github.ovso.righttoknow.ui.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnEndlessRecyclerScrollListener internal constructor(builder: Builder) : RecyclerView.OnScrollListener() {
  private var visibleThreshold = 1
  private var lastVisibleItem = 0
  private var totalItemCount = 0
  private var loading = false
  private val linearLayoutManager: LinearLayoutManager
  private val onLoadMoreListener: OnLoadMoreListener?
  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    super.onScrolled(recyclerView, dx, dy)
    totalItemCount = linearLayoutManager.itemCount
    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
    if (!loading && totalItemCount <= lastVisibleItem + visibleThreshold) {
      loading = true
      onLoadMoreListener?.onLoadMore()
    }
  }

  fun setLoaded() {
    loading = false
  }

  interface OnLoadMoreListener {
    fun onLoadMore()
  }

  class Builder(val linearLayoutManager: LinearLayoutManager,
               val  onLoadMoreListener: OnLoadMoreListener) : IBuilder<OnEndlessRecyclerScrollListener?> {
    var visibleThreshold = 1

    fun setVisibleThreshold(thresHold:Int = 1):Builder {
      visibleThreshold = thresHold
      return this
    }
    override fun build(): OnEndlessRecyclerScrollListener {
      return OnEndlessRecyclerScrollListener(this)
    }

  }

  init {
    linearLayoutManager = builder.linearLayoutManager
    onLoadMoreListener = builder.onLoadMoreListener
    visibleThreshold = builder.visibleThreshold
  }
}
package io.github.ovso.righttoknow.ui.base

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.righttoknow.data.network.model.video.SearchItem
import io.github.ovso.righttoknow.framework.utils.ObjectUtils
import io.github.ovso.righttoknow.ui.main.video.VideoAdapter

class VideoRecyclerView : RecyclerView {
  private var onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener<SearchItem>? = null

  var onEndlessRecyclerScrollListener: OnEndlessRecyclerScrollListener? = null

  constructor(context: Context) : super(context) {}
  constructor(context: Context,
              attrs: AttributeSet?) : super(context, attrs) {
  }

  constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

  override fun setAdapter(adapter: Adapter<*>?) {
    super.setAdapter(adapter)
    setOnItemClickListener(adapter)
  }

  fun setOnItemClickListener(listener: OnRecyclerViewItemClickListener<SearchItem>?) {
    onRecyclerViewItemClickListener = listener
    setOnItemClickListener(adapter)
  }

  private fun setOnItemClickListener(adapter: Adapter<*>?) {
    if (!ObjectUtils.isEmpty(adapter)) {
      if (adapter is VideoAdapter) {
        adapter.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener
      }
    }
  }

  override fun addOnScrollListener(listener: OnScrollListener) {
    super.addOnScrollListener(listener)
    onEndlessRecyclerScrollListener = listener as OnEndlessRecyclerScrollListener
  }
}
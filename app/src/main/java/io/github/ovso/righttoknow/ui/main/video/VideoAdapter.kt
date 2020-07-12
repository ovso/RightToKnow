package io.github.ovso.righttoknow.ui.main.video

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.righttoknow.data.network.model.video.SearchItem
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView
import io.github.ovso.righttoknow.ui.base.OnRecyclerViewItemClickListener
import io.github.ovso.righttoknow.ui.main.video.adapter.Bindable
import io.github.ovso.righttoknow.ui.main.video.adapter.VideoViewHolder
import java.util.*

class VideoAdapter : RecyclerView.Adapter<VideoViewHolder>(), BaseAdapterDataModel<SearchItem>, BaseAdapterView {
  private val items: MutableList<SearchItem> = ArrayList()

  var onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener<SearchItem>? = null
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
    return VideoViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
    (holder as Bindable<SearchItem>).bind(getItem(position))
    holder.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener
  }

  override fun getItemCount(): Int {
    return size
  }

  override fun refresh() {
    notifyDataSetChanged()
  }

  override fun add(item: SearchItem) {
    items.add(item)
  }

  override fun addAll(items: List<SearchItem>) {
    this.items.addAll(items)
  }

  override fun remove(position: Int): SearchItem {
    return items.removeAt(position)
  }

  override fun getItem(position: Int): SearchItem {
    return items[position]
  }

  override fun add(index: Int, item: SearchItem) {
    items.add(index, item)
  }

  override val size: Int
    get() = items.size

  override fun clear() {
    items.clear()
  }
}
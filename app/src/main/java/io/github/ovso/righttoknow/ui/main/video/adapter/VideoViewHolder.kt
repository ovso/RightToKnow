package io.github.ovso.righttoknow.ui.main.video.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.data.network.model.video.SearchItem
import io.github.ovso.righttoknow.ui.base.OnRecyclerViewItemClickListener
import io.github.ovso.righttoknow.utils.DateUtils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_video.*

class VideoViewHolder private constructor(
  override val containerView: View
) : RecyclerView.ViewHolder(containerView), Bindable<SearchItem>, LayoutContainer {
  private var data: SearchItem? = null

  var onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener<SearchItem>? = null
  override fun bind(_item: SearchItem) {
    data = _item
    title_text_view.text = _item.snippet.title
    val date = _item.snippet.publishedAt
    date_text_view.text = DateUtils.getDate(date, "yyyy년 MM월 dd일 HH시 mm분")
    _item.snippet.publishedAt
    Glide.with(itemView.context)
      .load(_item.snippet.thumbnails.medium.url)
      .into(thumbnail_image_view)
    itemView.findViewById<ImageButton>(R.id.play_button).setOnClickListener {
      onRecyclerViewItemClickListener!!.onItemClick(it, data, 0)
    }
  }

  companion object {
    fun create(parent: ViewGroup): VideoViewHolder {
      return VideoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_video, parent, false)
      )
    }
  }

}
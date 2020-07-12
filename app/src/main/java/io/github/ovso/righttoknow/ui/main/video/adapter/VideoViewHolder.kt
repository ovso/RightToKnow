package io.github.ovso.righttoknow.ui.main.video.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.bumptech.glide.Glide
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.data.network.model.video.SearchItem
import io.github.ovso.righttoknow.ui.base.OnRecyclerViewItemClickListener
import io.github.ovso.righttoknow.utils.DateUtils
import kotlinx.android.extensions.LayoutContainer

class VideoViewHolder private constructor(itemView: View, override val containerView: View) : RecyclerView.ViewHolder(itemView), Bindable<SearchItem>, LayoutContainer {
  private var data: SearchItem? = null

  @BindView(R.id.thumbnail_image_view)
  var thumbnailImageView: AppCompatImageView? = null

  @BindView(R.id.title_text_view)
  var titleTextView: TextView? = null

  @BindView(R.id.date_text_view)
  var dateTextView: TextView? = null

  var onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener<SearchItem>? = null
  override fun bind(`$data`: SearchItem) {
    data = `$data`
    titleTextView!!.text = `$data`.snippet.title
    val date = `$data`.snippet.publishedAt
    dateTextView!!.text = DateUtils.getDate(date, "yyyy년 MM월 dd일 HH시 mm분")
    `$data`.snippet.publishedAt
    Glide.with(itemView.context)
      .load(`$data`.snippet.thumbnails.medium.url)
      .into(thumbnailImageView!!)
    itemView.findViewById<ImageButton>(R.id.play_button).setOnClickListener {
      onRecyclerViewItemClickListener!!.onItemClick(it, data, 0)
    }
  }

  companion object {
    fun create(parent: ViewGroup): VideoViewHolder {
      return VideoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_video, parent, false))
    }
  }

}
package io.github.ovso.righttoknow.ui.main.news

import android.text.SpannableString
import android.view.View
import androidx.core.text.HtmlCompat
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter
import io.github.ovso.righttoknow.framework.utils.Utility
import io.github.ovso.righttoknow.ui.main.news.model.News
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_news_item.*
import java.util.*

class NewsAdapter : BaseRecyclerAdapter(), NewsAdapterDataModel, BaseAdapterView {
  private val items: MutableList<News> = ArrayList()
  override fun createViewHolder(view: View, viewType: Int): BaseViewHolder {
    return NewsViewHolder(view)
  }

  override fun getLayoutRes(viewType: Int): Int {
    return R.layout.fragment_news_item
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    if (holder is NewsViewHolder) {
      holder.onBindViewHolder(getItem(position), position)
      holder.itemView.setOnClickListener {
        if (onRecyclerItemClickListener != null) {
          onRecyclerItemClickListener!!.onItemClick(getItem(position))
        }
      }
      holder.simple_news_imageview.setOnClickListener {
        onRecyclerItemClickListener!!.onSimpleNewsItemClick(getItem(position))
      }
    }
  }

  override fun getItemCount(): Int {
    return size
  }

  override fun add(item: News) {
    items.add(item)
  }

  override fun addAll(items: List<News>) {
    this.items.addAll(items)
  }

  override fun remove(position: Int): News {
    return items.removeAt(position)
  }

  override fun getItem(position: Int): News {
    return items[position]
  }

  override fun add(index: Int, item: News) {
    items.add(index, item)
  }

  override val size: Int
    get() = items.size

  override fun clear() {
    items.clear()
  }

  override fun refresh() {
    notifyDataSetChanged()
  }

  private var onRecyclerItemClickListener: OnNewsRecyclerItemClickListener<News>? = null
  override fun setOnItemClickListener(listener: OnNewsRecyclerItemClickListener<News>) {
    onRecyclerItemClickListener = listener
  }

  internal class NewsViewHolder(override val containerView: View?) : BaseViewHolder(containerView!!), LayoutContainer {

    fun onBindViewHolder(_item: News, position: Int) {
      val title = _item.title
      val spannableString = SpannableString(HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_COMPACT))
      title_textview.text = spannableString
      count_textview.text = (position + 1).toString()
      val date = Utility.convertDate(_item.pubDate, "yy-MM-dd")
      date_textview.text = date
    }
  }
}
package io.github.ovso.righttoknow.ui.main.news

import android.text.Html
import android.text.SpannableString
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter
import io.github.ovso.righttoknow.framework.utils.Utility
import io.github.ovso.righttoknow.ui.main.news.model.News
import java.util.*

class NewsAdapter : BaseRecyclerAdapter(), NewsAdapterDataModel, BaseAdapterView {
  private val items: MutableList<News> = ArrayList()
  override fun createViewHolder(view: View?, viewType: Int): BaseViewHolder {
    return NewsViewHolder(view)
  }

  override fun getLayoutRes(viewType: Int): Int {
    return R.layout.fragment_news_item
  }

  override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
    if (viewHolder is NewsViewHolder) {
      val holder = viewHolder
      val news = items[position]
      val title = news.title
      val spannableString = SpannableString(Html.fromHtml(title))
      holder.titleTextview!!.text = spannableString
      holder.countTextview!!.text = (position + 1).toString()
      val date = Utility.convertDate(news.pubDate, "yy-MM-dd")
      holder.dateTextView!!.text = date
      holder.itemView.setOnClickListener { view: View? ->
        if (onRecyclerItemClickListener != null) {
          onRecyclerItemClickListener!!.onItemClick(news)
        }
      }
      holder.imageView!!.setOnClickListener { view: View? -> onRecyclerItemClickListener!!.onSimpleNewsItemClick(news) }
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

  internal class NewsViewHolder(itemView: View?) : BaseViewHolder(itemView) {
    @BindView(R.id.title_textview)
    var titleTextview: TextView? = null

    @BindView(R.id.count_textview)
    var countTextview: TextView? = null

    @BindView(R.id.date_textview)
    var dateTextView: TextView? = null

    @BindView(R.id.simple_news_imageview)
    var imageView: ImageView? = null
  }
}
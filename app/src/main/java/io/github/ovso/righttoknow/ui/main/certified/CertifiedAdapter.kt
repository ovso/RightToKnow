package io.github.ovso.righttoknow.ui.main.certified

import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener
import io.github.ovso.righttoknow.ui.main.certified.model.ChildCertified
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_certified_item.*
import java.util.*
import java.util.concurrent.TimeUnit

class CertifiedAdapter : BaseRecyclerAdapter(), BaseAdapterView,
  BaseAdapterDataModel<ChildCertified> {
  private val items: MutableList<ChildCertified> = ArrayList()
  private val compositeDisposable = CompositeDisposable()
  override fun createViewHolder(view: View, viewType: Int): BaseViewHolder {
    return CertifiedViewHolder(view)
  }

  override fun getLayoutRes(viewType: Int): Int {
    return R.layout.fragment_certified_item
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    if (holder is CertifiedViewHolder) {
      val item = items[position]
      holder.onBindViewHolder(item)
      compositeDisposable.add(RxView.clicks(holder.itemView)
        .throttleFirst(1, TimeUnit.SECONDS)
        .subscribe { onRecyclerItemClickListener!!.onItemClick(item) })
    }
  }

  override fun getItemCount(): Int {
    return size
  }

  override fun refresh() {
    notifyDataSetChanged()
  }

  override fun add(item: ChildCertified) {
    items.add(item)
  }

  override fun addAll(items: List<ChildCertified>) {
    this.items.addAll(items)
  }

  override fun remove(position: Int): ChildCertified {
    return items.removeAt(position)
  }

  override fun getItem(position: Int): ChildCertified {
    return items[position]
  }

  override fun add(index: Int, item: ChildCertified) {
    items.add(index, item)
  }

  override val size: Int
    get() = items.size

  override fun clear() {
    items.clear()
  }

  var onRecyclerItemClickListener: OnRecyclerItemClickListener<ChildCertified>? = null
  fun onDestroyView() {
    compositeDisposable.dispose()
    compositeDisposable.clear()
  }

  internal class CertifiedViewHolder(override val containerView: View?) :
    BaseViewHolder(containerView!!),
    LayoutContainer {
    fun onBindViewHolder(item: ChildCertified) {
      title_textview.text = item.title.trim { it <= ' ' }.replace(" ", "\u00A0")
      order_textview.text = item.order.toString()
    }
  }
}

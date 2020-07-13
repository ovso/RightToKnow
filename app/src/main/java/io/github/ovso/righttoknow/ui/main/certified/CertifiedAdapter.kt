package io.github.ovso.righttoknow.ui.main.certified

import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.jakewharton.rxbinding2.view.RxView
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener
import io.github.ovso.righttoknow.ui.main.certified.model.ChildCertified
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import java.util.concurrent.TimeUnit

class CertifiedAdapter : BaseRecyclerAdapter(), BaseAdapterView, BaseAdapterDataModel<ChildCertified> {
  private val items: MutableList<ChildCertified> = ArrayList()
  private val compositeDisposable = CompositeDisposable()
  override fun createViewHolder(view: View, viewType: Int): BaseViewHolder {
    return CertifiedViewHolder(view)
  }

  override fun getLayoutRes(viewType: Int): Int {
    return R.layout.fragment_certified_item
  }

  override fun onBindViewHolder(baseHolder: BaseViewHolder, position: Int) {
    if (baseHolder is CertifiedViewHolder) {
      val item = items[position]
      baseHolder.titleTextview!!.text = item.title.trim { it <= ' ' }.replace(" ", "\u00A0")
      baseHolder.orderTextview!!.text = item.order.toString()
      //holder.itemView.setOnClickListener(view -> onRecyclerItemClickListener.onItemClick(item));
      compositeDisposable.add(RxView.clicks(baseHolder.itemView)
        .throttleFirst(1, TimeUnit.SECONDS)
        .subscribe({ onRecyclerItemClickListener!!.onItemClick(item) }))
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

  private val onRecyclerItemClickListener: OnRecyclerItemClickListener<ChildCertified>? = null
  fun onDestroyView() {
    compositeDisposable.dispose()
    compositeDisposable.clear()
  }

  internal class CertifiedViewHolder(itemView: View?) : BaseViewHolder((itemView)!!) {
    @JvmField
    @BindView(R.id.title_textview)
    var titleTextview: TextView? = null

    @JvmField
    @BindView(R.id.order_textview)
    var orderTextview: TextView? = null
  }
}
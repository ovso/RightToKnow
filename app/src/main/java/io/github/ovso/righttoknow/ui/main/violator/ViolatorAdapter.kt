package io.github.ovso.righttoknow.ui.main.violator

import android.view.View
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener
import io.github.ovso.righttoknow.ui.main.violator.model.Violator
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_violator_item.*
import java.util.*

class ViolatorAdapter : BaseRecyclerAdapter(), BaseAdapterView,
  ViolatorAdapterDataModel<Violator> {
  private val items: MutableList<Violator> = ArrayList()
  override fun createViewHolder(view: View, viewType: Int): BaseViewHolder {
    return ViolatorViewHolder(view)
  }

  override fun getLayoutRes(viewType: Int): Int {
    return R.layout.fragment_violator_item
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    if (holder is ViolatorViewHolder) {
      holder.onBindViewHolder(getItem(position))
      holder.itemView.setOnClickListener {
        onRecyclerItemClickListener.onItemClick(
          getItem(
            position
          )
        )
      }
    }
  }

  override fun getItemCount(): Int {
    return size
  }

  override fun refresh() {
    notifyDataSetChanged()
  }

  override fun add(item: Violator) {
    items.add(item)
  }

  override fun addAll(items: List<Violator>) {
    this.items.addAll(items)
  }

  override fun remove(position: Int): Violator {
    return items.removeAt(position)
  }

  override fun getItem(position: Int): Violator {
    return items[position]
  }

  override fun add(index: Int, item: Violator) {
    items.add(index, item)
  }

  override val size: Int
    get() = items.size

  lateinit var onRecyclerItemClickListener: OnRecyclerItemClickListener<Violator>

  internal class ViolatorViewHolder(override val containerView: View?) :
    BaseViewHolder(containerView!!), LayoutContainer {
    fun onBindViewHolder(item: Violator) {
      order_textview.text = item.order.toString()
      sido_textview.text = item.sido
      sigungu_textview.text = item.sigungu
      name_textview.text = item.name
      vio_fac_textview.text = item.fac_name
      history_textview.text = item.history
    }
  }

  override fun clear() {
    items.clear()
  }
}

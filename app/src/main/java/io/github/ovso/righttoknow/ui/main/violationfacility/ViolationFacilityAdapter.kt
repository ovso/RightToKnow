package io.github.ovso.righttoknow.ui.main.violationfacility

import android.view.View
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener
import io.github.ovso.righttoknow.ui.main.violationfacility.model.VioFac
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_violation_item.*
import java.util.*

class ViolationFacilityAdapter : BaseRecyclerAdapter(), BaseAdapterView,
  BaseAdapterDataModel<VioFac> {
  private val items: MutableList<VioFac> = ArrayList()
  override fun createViewHolder(view: View, viewType: Int): BaseViewHolder {
    return ViolationFacilityViewHolder(view)
  }

  override fun getLayoutRes(viewType: Int): Int {
    return R.layout.fragment_violation_item
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    if (holder is ViolationFacilityViewHolder) {
      holder.onBindViewHolder(getItem(position))
      holder.itemView.setOnClickListener {
        onRecyclerItemClickListener!!.onItemClick(
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

  override fun add(item: VioFac) {
    items.add(item)
  }

  override fun addAll(items: List<VioFac>) {
    this.items.addAll(items)
  }

  override fun remove(position: Int): VioFac {
    return items.removeAt(position)
  }

  override fun getItem(position: Int): VioFac {
    return items[position]
  }

  override fun add(index: Int, item: VioFac) {
    items.add(index, item)
  }

  override val size: Int
    get() = items.size

  override fun clear() {
    items.clear()
  }

  var onRecyclerItemClickListener: OnRecyclerItemClickListener<VioFac>? = null

  internal class ViolationFacilityViewHolder(override val containerView: View?) :
    BaseViewHolder(containerView!!), LayoutContainer {
    fun onBindViewHolder(item: VioFac) {
      order_textview.text = item.order.toString()
      sido_textview.text = item.sido
      sigungu_textview.text = item.sigungu
      fac_name_textview.text = item.fac_name
      type_textview.text = item.type
    }
  }
}

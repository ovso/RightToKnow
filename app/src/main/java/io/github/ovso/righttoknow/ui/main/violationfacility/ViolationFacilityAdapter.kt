package io.github.ovso.righttoknow.ui.main.violationfacility

import android.view.View
import android.widget.TextView
import butterknife.BindView
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener
import io.github.ovso.righttoknow.ui.main.violationfacility.model.VioFac
import java.util.*

class ViolationFacilityAdapter : BaseRecyclerAdapter(), BaseAdapterView, BaseAdapterDataModel<VioFac> {
  private val items: MutableList<VioFac> = ArrayList()
  override fun createViewHolder(view: View, viewType: Int): BaseViewHolder {
    return ViolationFacilityViewHolder(view)
  }

  override fun getLayoutRes(viewType: Int): Int {
    return R.layout.fragment_violation_item
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    if (holder is ViolationFacilityViewHolder) {
      val fac = items[position]
      holder.orderTextView!!.text = fac.order.toString()
      holder.sidoTextView!!.text = fac.sido
      holder.sigunguTextView!!.text = fac.sigungu
      holder.facNameTextView!!.text = fac.fac_name
      holder.typeTextView!!.text = fac.type
      holder.itemView.setOnClickListener { onRecyclerItemClickListener!!.onItemClick(fac) }
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

  internal class ViolationFacilityViewHolder(itemView: View) : BaseViewHolder(itemView) {
    @JvmField
    @BindView(R.id.order_textview)
    var orderTextView: TextView? = null

    @JvmField
    @BindView(R.id.sido_textview)
    var sidoTextView: TextView? = null

    @JvmField
    @BindView(R.id.sigungu_textview)
    var sigunguTextView: TextView? = null

    @JvmField
    @BindView(R.id.fac_name_textview)
    var facNameTextView: TextView? = null

    @JvmField
    @BindView(R.id.type_textview)
    var typeTextView: TextView? = null
  }
}
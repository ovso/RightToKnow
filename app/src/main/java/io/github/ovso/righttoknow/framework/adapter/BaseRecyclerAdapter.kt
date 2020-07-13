package io.github.ovso.righttoknow.framework.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter : RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(getLayoutRes(viewType), parent, false)
    return createViewHolder(view, viewType)
  }

  protected abstract fun createViewHolder(view: View, viewType: Int): BaseViewHolder

  @LayoutRes
  abstract fun getLayoutRes(viewType: Int): Int
  abstract override fun onBindViewHolder(holder: BaseViewHolder, position: Int)
  abstract override fun getItemCount(): Int
  open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
  companion object {
    protected const val ITEM_VIEW_TYPE_HEADER = 0
    protected const val ITEM_VIEW_TYPE_DEFAULT = 1
  }
}

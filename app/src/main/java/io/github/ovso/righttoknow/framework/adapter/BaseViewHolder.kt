package io.github.ovso.righttoknow.framework.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife

class BaseViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
  init {
    ButterKnife.bind(this, itemView!!)
  }
}
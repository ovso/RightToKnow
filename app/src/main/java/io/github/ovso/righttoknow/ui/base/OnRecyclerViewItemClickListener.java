package io.github.ovso.righttoknow.ui.base;

import android.view.View;

public interface OnRecyclerViewItemClickListener<T> {
  void onItemClick(View view, T data, int itemPosition);
}
package io.github.ovso.righttoknow.ui.main.news;

import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;

/**
 * Created by jaeho on 2017. 12. 20
 */

public interface OnNewsRecyclerItemClickListener<T> extends OnRecyclerItemClickListener<T> {
  public void onSimpleNewsItemClick(T item);
}

package io.github.ovso.righttoknow.adapter;

import java.util.List;

/**
 * Created by jaeho on 2017. 8. 1
 */

public interface BaseAdapterDataModel<T> {
  void add(T item);
  void addAll(List<T> items);
  T remove(int position);
  T getItem(int position);

  int getSize();

  void clear();
}

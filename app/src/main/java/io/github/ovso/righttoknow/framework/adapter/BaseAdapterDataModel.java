package io.github.ovso.righttoknow.framework.adapter;

import java.util.List;

public interface BaseAdapterDataModel<T> {
  void add(T item);
  void addAll(List<T> items);
  T remove(int position);
  T getItem(int position);
  void add(int index, T item);
  int getSize();

  void clear();
}

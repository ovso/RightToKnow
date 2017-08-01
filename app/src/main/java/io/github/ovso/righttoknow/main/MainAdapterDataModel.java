package io.github.ovso.righttoknow.main;

import java.util.List;

/**
 * Created by jaeho on 2017. 8. 1
 */

public interface MainAdapterDataModel<T> {
  void add(T item);
  void addAll(List<T> items);
  T remove(int position);
  T getItem(int position);

  int getSize();
}

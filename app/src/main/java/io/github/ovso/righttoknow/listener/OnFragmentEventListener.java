package io.github.ovso.righttoknow.listener;

/**
 * Created by jaeho on 2017. 8. 8
 */

public interface OnFragmentEventListener<T> {
  void onNearbyClick();
  void onSearchQuery(String query);
}
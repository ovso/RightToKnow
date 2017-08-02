package io.github.ovso.righttoknow.listener;

/**
 * Created by jaeho on 2017. 8. 2
 */

public interface OnViolationFacilityResultListener<T> {
  void onPre();
  void onResult(T result);
  void onPost();
}
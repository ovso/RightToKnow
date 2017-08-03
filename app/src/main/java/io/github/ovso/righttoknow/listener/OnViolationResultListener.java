package io.github.ovso.righttoknow.listener;

/**
 * Created by jaeho on 2017. 8. 2
 */

public interface OnViolationResultListener<T> {
  void onPre();
  void onResult(T result);
  void onPost();
}
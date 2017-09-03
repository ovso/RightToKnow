package io.github.ovso.righttoknow.listener;

/**
 * Created by jaeho on 2017. 8. 2
 */

public interface OnChildResultListener<T> {
  void onPre();
  void onResult(T result);
  void onPost();
  void onError();
}
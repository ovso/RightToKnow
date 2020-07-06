package io.github.ovso.righttoknow.framework.listener

interface OnChildResultListener<T> {
  fun onPre()
  fun onResult(result: T)
  fun onPost()
  fun onError()
}
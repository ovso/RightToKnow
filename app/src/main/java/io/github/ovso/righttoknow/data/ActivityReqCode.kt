package io.github.ovso.righttoknow.data

enum class ActivityReqCode(private val value: Int) {
  YOUTUBE(0);

  fun get(): Int {
    return value
  }

}
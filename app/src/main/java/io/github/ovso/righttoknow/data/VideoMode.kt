package io.github.ovso.righttoknow.data

enum class VideoMode(var value: Int) {
  PORTRAIT(-1),
  CANCEL(-2),
  LANDSCAPE(-3);

  fun get(): Int {
    return value
  }

  companion object {
    @JvmStatic
    fun toMode(which: Int): VideoMode {
      for (i in values().indices) {
        if (values()[i].get() == which) {
          return values()[i]
        }
      }
      return PORTRAIT
    }
  }

}
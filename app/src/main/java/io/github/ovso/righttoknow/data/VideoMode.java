package io.github.ovso.righttoknow.data;

import androidx.annotation.NonNull;

public enum VideoMode {
  PORTRAIT(-1), CANCEL(-2), LANDSCAPE(-3);
  int value;

  VideoMode(int $value) {
    value = $value;
  }

  public int get() {
    return value;
  }

  @NonNull public static VideoMode toMode(int which) {
    for (int i = 0; i < VideoMode.values().length; i++) {
      if (VideoMode.values()[i].get() == which) {
        return VideoMode.values()[i];
      }
    }
    return PORTRAIT;
  }
}

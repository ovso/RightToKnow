package io.github.ovso.righttoknow.framework.utils;

import lombok.Getter;

@Getter public enum TimeoutMillis {
  JSOUP(1000 * 10);

  private int value;

  TimeoutMillis(int value) {
    this.value = value;
  }
}

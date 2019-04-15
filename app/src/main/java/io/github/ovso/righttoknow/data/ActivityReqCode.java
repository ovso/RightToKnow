package io.github.ovso.righttoknow.data;

public enum ActivityReqCode {
  YOUTUBE(0);

  private int value;

  ActivityReqCode(int $value) {
    this.value = $value;
  }

  public int get() {
    return value;
  }
}

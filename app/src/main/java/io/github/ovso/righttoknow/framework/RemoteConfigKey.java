package io.github.ovso.righttoknow.framework;

public enum RemoteConfigKey {
  AD_ADMOB("admob"), WINK("wink");

  private String value;

  RemoteConfigKey(String value) {
    this.value = value;
  }
}

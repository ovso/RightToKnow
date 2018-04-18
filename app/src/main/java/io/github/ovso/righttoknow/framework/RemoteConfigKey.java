package io.github.ovso.righttoknow.framework;

public enum RemoteConfigKey {
  AD_ADMOB("admob"), AD_CAULY("cauly"), AD_ALL("all");

  private String value;

  RemoteConfigKey(String value) {
    this.value = value;
  }
}

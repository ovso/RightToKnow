package io.github.ovso.righttoknow.data

enum class KeyName(val value: String) {
  POSITION("position"),
  PAGE_TOKEN("pageToken"),
  Q("q"),
  MAX_RESULTS("maxResults"),
  ORDER("order"),
  TYPE("type"),
  VIDEO_SYNDICATED("videoSyndicated"),
  KEY("key"),
  PART("part");

  fun get(): String {
    return value
  }

}
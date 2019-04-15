package io.github.ovso.righttoknow.data.network;

import lombok.Getter;

@Getter public enum ApiEndPoint {
  SEARCH("https://www.googleapis.com/");

  ApiEndPoint(String $value) {
    url = $value;
  }

  private String url;
}
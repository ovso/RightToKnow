package io.github.ovso.righttoknow.data.network.model.video;

import lombok.Getter;

@Getter public class SearchItem {
  private String kind;
  private String etag;
  private SearchItemId id;
  private Snippet snippet;
}
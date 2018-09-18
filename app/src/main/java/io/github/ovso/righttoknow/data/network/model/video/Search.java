package io.github.ovso.righttoknow.data.network.model.video;

import java.util.List;
import lombok.Getter;

@Getter public class Search {
  private String kind;
  private String etag;
  private String nextPageToken;
  private String regionCode;
  private SearchPageInfo pageInfo;
  private List<SearchItem> items;
}
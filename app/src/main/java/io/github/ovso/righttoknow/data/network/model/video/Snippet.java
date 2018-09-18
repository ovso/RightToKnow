package io.github.ovso.righttoknow.data.network.model.video;

import lombok.Getter;

@Getter public class Snippet {
  private String publishedAt;
  private String channelId;
  private String title;
  private String description;
  private SearchThumbnails thumbnails;
  private String channelTitle;
  private String liveBroadcastContent;
}
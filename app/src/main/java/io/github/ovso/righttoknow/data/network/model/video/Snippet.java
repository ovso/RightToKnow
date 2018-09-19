package io.github.ovso.righttoknow.data.network.model.video;

import java.util.Date;
import lombok.Getter;

@Getter public class Snippet {
  private Date publishedAt;
  private String channelId;
  private String title;
  private String description;
  private SearchThumbnails thumbnails;
  private String channelTitle;
  private String liveBroadcastContent;
}
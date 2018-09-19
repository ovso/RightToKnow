package io.github.ovso.righttoknow.data.network.model.video;

import lombok.Getter;

@Getter public class SearchThumbnails {
  private ThumbnailsInfo defaults;
  private ThumbnailsInfo medium;
  private ThumbnailsInfo high;
}
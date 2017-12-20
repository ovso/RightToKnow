package io.github.ovso.righttoknow.news.model;

import java.io.Serializable;
import lombok.Getter;

/**
 * Created by jaeho on 2017. 9. 1
 */

@Getter public class News implements Serializable {
  private String title;
  private String originallink;
  private String link;
  private String description;
  private String pubDate;
}

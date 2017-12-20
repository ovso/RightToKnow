package io.github.ovso.righttoknow.news.model;

import java.util.List;
import lombok.Getter;

/**
 * Created by jaeho on 2017. 12. 20
 */

@Getter public class NewsResult {
  private String lastBuildDate;
  private int total;
  private int start;
  private int display;
  private List<News> items;
}

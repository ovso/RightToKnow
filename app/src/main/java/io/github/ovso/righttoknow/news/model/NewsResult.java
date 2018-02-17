package io.github.ovso.righttoknow.news.model;

import io.github.ovso.righttoknow.common.Utility;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

  public static List<News> sortItems(List<News> items) {
    Collections.sort(items, (o1, o2) -> {
      String pattern = "yy-MM-dd";
      try {
        String o1String = Utility.convertDate(o1.getPubDate(), pattern);
        String o2String = Utility.convertDate(o2.getPubDate(), pattern);

        Date o1Date = new SimpleDateFormat(pattern).parse(o1String);
        Date o2Date = new SimpleDateFormat(pattern).parse(o2String);
        return o2Date.compareTo(o1Date);
      } catch (ParseException e) {
        e.printStackTrace();
        return 0;
      }
    });

    return items;
  }

  public static List<News> mergeItems(List<NewsResult> results) {
    final List<News> items = new ArrayList<>();
    for (NewsResult result : results) {
      items.addAll(result.getItems());
    }
    return items;
  }
}

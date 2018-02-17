package io.github.ovso.righttoknow.common;

import java.util.List;

/**
 * Created by jaeho on 2018. 2. 17
 */

public class SearchUtils {

  public static boolean isListContains(List<String> items, String query) {
    for (String item : items) {
      if (item.contains(query)) return true;
    }
    return false;
  }

  public static boolean isTextContains(String text, String query) {
    return text.contains(query);
  }
}
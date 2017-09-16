package io.github.ovso.righttoknow.common;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class ObjectUtils {

  public static boolean isEmpty(Object object) {
    return object == null;
  }

  public static boolean isEmpty(Object[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(CharSequence str) {
    return (str == null || str.length() == 0);
  }

  @SuppressWarnings("rawtypes") public static boolean isEmpty(Collection collection) {
    return collection == null || collection.isEmpty();
  }

  @SuppressWarnings("rawtypes") public static boolean isEmpty(Map map) {
    return map == null || map.isEmpty();
  }

  public static boolean isEmpty(List<?> list) {
    return list == null || list.isEmpty();
  }
}

package io.github.ovso.righttoknow.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

  private DateUtils() {
  }

  public static String getDate(Date date, String format) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    return simpleDateFormat.format(date);
  }
}
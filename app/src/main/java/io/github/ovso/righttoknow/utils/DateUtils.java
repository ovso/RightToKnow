package io.github.ovso.righttoknow.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.joda.time.DateTime;

public final class DateUtils {

  private DateUtils() {
  }

  public static String getDate(Date date, String format) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
    return simpleDateFormat.format(date);
  }

  public static long diffOfDate(DateTime begin, DateTime end) {
    long diff = end.getMillis() - begin.getMillis();
    return Math.abs(diff / (24 * 60 * 60 * 1000));
  }
}
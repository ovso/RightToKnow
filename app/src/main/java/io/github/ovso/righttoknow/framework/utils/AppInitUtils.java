package io.github.ovso.righttoknow.framework.utils;

import android.content.Context;
import com.downloader.PRDownloader;
import com.google.android.gms.ads.MobileAds;
import io.github.ovso.righttoknow.Security;
import timber.log.Timber;

public final class AppInitUtils {

  private AppInitUtils() {
  }

  public static void ad(Context context) {
    MobileAds.initialize(context, Security.ADMOB_APP_ID.getValue());
    // do something..
  }

  public static boolean debug(Context context) {
    return SystemUtils.isDebuggable(context);
  }

  public static void timber(boolean debug) {
    if (debug) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  public static void prDownloader(Context context) {
    PRDownloader.initialize(context);
  }
}

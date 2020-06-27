package io.github.ovso.righttoknow.framework.utils;

import android.content.Context;

import com.downloader.PRDownloader;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.net.SocketException;

import io.github.ovso.righttoknow.Security;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

public final class AppInitUtils {

  private AppInitUtils() {}

  public static void ad(Context context) {
//    MobileAds.initialize(context, Security.ADMOB_APP_ID.getValue());
    MobileAds.initialize(context, initializationStatus -> {});
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

  //noinspection UnnecessaryParentheses
  public static void rxJavaPlugin() {
    RxJavaPlugins.setErrorHandler(
        e -> {
          if (e instanceof UndeliverableException) {
            e = e.getCause();
          }
          if ((e instanceof IOException) || (e instanceof SocketException)) {
            // fine, irrelevant network problem or API that throws on cancellation
            return;
          }
          if (e instanceof InterruptedException) {
            // fine, some blocking code was interrupted by a dispose call
            return;
          }
          if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
            // that's likely a bug in the application
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler =
                Thread.currentThread().getUncaughtExceptionHandler();
            if (uncaughtExceptionHandler != null) {
              uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e);
            }
            return;
          }
          if (e instanceof IllegalStateException) {
            // that's a bug in RxJava or in a custom operator
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler =
                Thread.currentThread().getUncaughtExceptionHandler();
            if (uncaughtExceptionHandler != null) {
              uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e);
            }
            return;
          }
          Timber.tag("Undeliverable exception").w(e);
        });
  }
}

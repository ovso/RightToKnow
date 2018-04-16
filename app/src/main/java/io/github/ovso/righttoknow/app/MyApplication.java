package io.github.ovso.righttoknow.app;

import android.app.Application;
import com.downloader.PRDownloader;
import com.google.android.gms.ads.MobileAds;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.common.MessagingHandler;
import io.github.ovso.righttoknow.framework.SystemUtils;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class MyApplication extends Application {
  public static boolean DEBUG = false;
  private static MyApplication instance;

  public static MyApplication getInstance() {
    return instance;
  }

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    initDebuggable();
    initTimber();
    initFileDownloader();
    initAdmob();
    MessagingHandler.createChannelToShowNotifications();
  }

  private void initAdmob() {
    MobileAds.initialize(this, Security.getAdmobAppId(DEBUG));
  }

  private void initFileDownloader() {
    PRDownloader.initialize(getApplicationContext());
  }

  private void initTimber() {
    if (DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  private void initDebuggable() {
    this.DEBUG = SystemUtils.isDebuggable(this);
  }
}
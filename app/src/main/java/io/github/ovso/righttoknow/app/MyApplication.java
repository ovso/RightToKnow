package io.github.ovso.righttoknow.app;

import com.downloader.PRDownloader;
import com.google.android.gms.ads.MobileAds;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.framework.di.DaggerAppComponent;
import io.github.ovso.righttoknow.framework.utils.MessagingHandler;
import io.github.ovso.righttoknow.framework.utils.SystemUtils;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class MyApplication extends DaggerApplication {
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
    MobileAds.initialize(this, Security.ADMOB_UNIT_ID.getValue());
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

  @Override protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerAppComponent.builder().application(this).build();
  }
}
package io.github.ovso.righttoknow;

import com.downloader.PRDownloader;
import com.google.android.gms.ads.MobileAds;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.github.ovso.righttoknow.framework.di.DaggerAppComponent;
import io.github.ovso.righttoknow.framework.utils.MessagingHandler;
import io.github.ovso.righttoknow.framework.utils.SystemUtils;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class App extends DaggerApplication {
  public static boolean DEBUG = false;
  private static App instance;

  public static App getInstance() {
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
    MobileAds.initialize(this, Security.ADMOB_APP_ID.getValue());
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
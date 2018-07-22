package io.github.ovso.righttoknow;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.github.ovso.righttoknow.framework.di.DaggerAppComponent;
import io.github.ovso.righttoknow.framework.utils.AppInitUtils;
import io.github.ovso.righttoknow.framework.utils.MessagingHandler;

public class App extends DaggerApplication {
  public static boolean DEBUG = false;
  private static App instance;

  public static App getInstance() {
    return instance;
  }

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    DEBUG = AppInitUtils.debug(getApplicationContext());
    AppInitUtils.ad(getApplicationContext());
    AppInitUtils.timber(DEBUG);
    AppInitUtils.prDownloader(getApplicationContext());
    MessagingHandler.createChannelToShowNotifications();
  }

  @Override protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerAppComponent.builder().application(this).build();
  }
}
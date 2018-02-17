package io.github.ovso.righttoknow.app;

import android.app.Application;
import io.github.ovso.righttoknow.common.MessagingHandler;
import io.github.ovso.righttoknow.framework.SystemUtils;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class MyApplication extends Application {
  private static MyApplication instance;
  public static boolean DEBUG = false;

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    initDebuggable();
    initTimber();
    MessagingHandler.createChannelToShowNotifications();
  }

  private void initTimber() {
    if (DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  private void initDebuggable() {
    this.DEBUG = SystemUtils.isDebuggable(this);
  }

  public static MyApplication getInstance() {
    return instance;
  }
}
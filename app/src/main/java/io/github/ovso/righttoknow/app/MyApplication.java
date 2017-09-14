package io.github.ovso.righttoknow.app;

import android.app.Application;
import io.github.ovso.righttoknow.common.MessagingHandler;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class MyApplication extends Application {
  private static MyApplication instance;

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    MessagingHandler.createChannelToShowNotifications();
  }

  public static MyApplication getInstance() {
    return instance;
  }
}
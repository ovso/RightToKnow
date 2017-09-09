package io.github.ovso.righttoknow.app;

import android.app.Application;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class MyApplication extends Application {
  private static MyApplication instance;

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
  }

  public static MyApplication getInstance() {
    return instance;
  }
}
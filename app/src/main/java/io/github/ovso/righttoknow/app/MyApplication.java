package io.github.ovso.righttoknow.app;

import android.app.Application;
import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.codewaves.youtubethumbnailview.downloader.OembedVideoInfoDownloader;
import io.github.ovso.righttoknow.common.Constants;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class MyApplication extends Application {
  private static MyApplication instance;
  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    ThumbnailLoader.initialize(Constants.DEVELOPER_KEY);
    ThumbnailLoader.initialize()
        .setVideoInfoDownloader(new OembedVideoInfoDownloader());
  }

  public static MyApplication getInstance() {
    return instance;
  }
}
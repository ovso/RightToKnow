package io.github.ovso.righttoknow.framework.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.App;
import java.util.HashMap;

/**
 * Created by jaeho on 2017. 9. 14
 */

public class MessagingHandler {

  public static void createChannelToShowNotifications() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      Context context = App.getInstance();
      String channelId = context.getString(R.string.default_notification_channel_id);
      String channelName = context.getString(R.string.default_notification_channel_name);
      NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(
          new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW));
    }
  }

  public static int getContentPosition(Bundle extras) {
    HashMap<String, Object> map = new HashMap<>();
    for (String key : extras.keySet()) {
      map.put(key, extras.get(key));
      if (key.contains(Constants.FCM_KEY_CONTENT_POSITION)) {
        try {
          return Integer.parseInt(extras.get(key).toString());
        } catch (NumberFormatException e) {
          e.printStackTrace();
          return 0;
        }
      }
    }
    return 0;
  }

  public static boolean isUpdate(String storeVersionName) {
    String versionName = storeVersionName.replaceAll("\\.", "");
    int storeVersionNumber;
    try {
      storeVersionNumber = Integer.valueOf(versionName);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      storeVersionNumber = 0;
    }
    String replaceVersion =
        Utility.getVersionName(App.getInstance()).replaceAll("\\.", "");
    int appVersionNumber;
    try {
      appVersionNumber = Integer.parseInt(replaceVersion);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      appVersionNumber = 0;
    }

    return storeVersionNumber > appVersionNumber;
  }
}

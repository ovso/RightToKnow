package io.github.ovso.righttoknow.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import hugo.weaving.DebugLog;

/**
 * Created by jaeho on 2017. 9. 14
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
  public final static String TAG = "MyFirebaseMessagingService";
  @DebugLog @Override public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
  }
}

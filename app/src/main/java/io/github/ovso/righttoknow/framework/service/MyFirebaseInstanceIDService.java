package io.github.ovso.righttoknow.framework.service;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import hugo.weaving.DebugLog;

/**
 * Created by jaeho on 2017. 9. 14
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
  private final static String TAG = "MyFirebaseInstanceIDService";

  @DebugLog @Override public void onTokenRefresh() {
    super.onTokenRefresh();
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    Log.d(TAG, "Refreshed token: " + refreshedToken);
    sendRegistrationToServer(refreshedToken);
  }

  @DebugLog private void sendRegistrationToServer(String token) {
    // TODO: Implement this method to send token to your app server.
  }
}

package io.github.ovso.righttoknow.framework.firebase;

import android.app.Activity;
import android.widget.Toast;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import java.lang.ref.WeakReference;
import lombok.Setter;
import lombok.experimental.Accessors;

public class MyFirebaseRemoteConfig {
  private static final String ADVIEW_PARAMETER_KEY = "adview_type";
  private static final String ADVIEW_ADMOB_VALUE = "admob";
  private static final String ADVIEW_CAULY_VALUE = "cauly";
  private FirebaseRemoteConfig firebaseRemoteConfig;
  private Activity activity;
  private OnMyFirebaseRemoteConfigListener listener;

  private MyFirebaseRemoteConfig(Builder builder) {
    this.listener = builder.listener;
  }

  public void init(Activity activity) {
    this.activity = new WeakReference<>(activity).get();
    firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
        .setDeveloperModeEnabled(MyApplication.DEBUG)
        .build();
    firebaseRemoteConfig.setConfigSettings(configSettings);
    firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

    fetchWelcome();
  }

  /**
   * Fetch a welcome message from the Remote Config service, and then activate it.
   */
  private void fetchWelcome() {
    long cacheExpiration = 3600; // 1 hour in seconds.
    // If your app is using developer mode, cacheExpiration is set to 0, so each fetch will
    // retrieve values from the service.
    if (firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
      cacheExpiration = 0;
    }

    // [START fetch_config_with_callback]
    // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
    // will use fetch data from the Remote Config service, rather than cached parameter values,
    // if cached parameter values are more than cacheExpiration seconds old.
    // See Best Practices in the README for more information.
    firebaseRemoteConfig.fetch(cacheExpiration)
        .addOnCompleteListener(activity, task -> {
          if (task.isSuccessful()) {
            Toast.makeText(activity, "Fetch Succeeded",
                Toast.LENGTH_SHORT).show();

            // After config data is successfully fetched, it must be activated before newly fetched
            // values are returned.
            firebaseRemoteConfig.activateFetched();
          } else {
            Toast.makeText(activity, "Fetch Failed",
                Toast.LENGTH_SHORT).show();
          }
          displayWelcomeMessage();
        });
    // [END fetch_config_with_callback]
  }

  private void displayWelcomeMessage() {
    // [START get_config_values]
    String adviewValue = firebaseRemoteConfig.getString(ADVIEW_PARAMETER_KEY);
    // [END get_config_values]
    if (adviewValue.equals(ADVIEW_ADMOB_VALUE)) {
      listener.onAdmob();
    } else if (adviewValue.equals(ADVIEW_CAULY_VALUE)) {
      listener.onCauly();
    } else {
      listener.onFailure();
    }
  }

  public static class Builder {
    @Setter @Accessors(chain = true) private OnMyFirebaseRemoteConfigListener listener;

    public MyFirebaseRemoteConfig build() {
      return new MyFirebaseRemoteConfig(this);
    }
  }
}

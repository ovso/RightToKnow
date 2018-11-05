package io.github.ovso.righttoknow.framework.utils;

import android.content.Context;
import com.downloader.PRDownloader;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import io.github.ovso.righttoknow.BuildConfig;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.Security;
import net.danlew.android.joda.JodaTimeAndroid;
import timber.log.Timber;

public final class AppInitUtils {

  private AppInitUtils() {
  }

  public static void ad(Context context) {
    MobileAds.initialize(context, Security.ADMOB_APP_ID.getValue());
    // do something..
  }

  public static boolean debug(Context context) {
    return SystemUtils.isDebuggable(context);
  }

  public static void timber(boolean debug) {
    if (debug) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  public static void prDownloader(Context context) {
    PRDownloader.initialize(context);
  }

  public static void joda(Context context) {
    JodaTimeAndroid.init(context);
  }

  public static void config() {
    FirebaseRemoteConfig instance = FirebaseRemoteConfig.getInstance();
    FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
        .setDeveloperModeEnabled(BuildConfig.DEBUG)
        .build();
    instance.setConfigSettings(configSettings);
    instance.setDefaults(R.xml.remote_config_defaults);

    /*
    long cacheExpiration = 3600;
    if (instance.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
      cacheExpiration = 0;
    }

    instance.fetch(cacheExpiration)
        .addOnCompleteListener(task -> {
          if (task.isSuccessful()) {

            // After config data is successfully fetched, it must be activated before newly fetched
            // values are returned.
            instance.activateFetched();
            String ad_type = instance.getString("ad_type");
            Toast.makeText(context, ad_type,
                Toast.LENGTH_SHORT).show();

          } else {
            Toast.makeText(context, "Fetch Failed",
                Toast.LENGTH_SHORT).show();
          }
        });
    */
  }
}

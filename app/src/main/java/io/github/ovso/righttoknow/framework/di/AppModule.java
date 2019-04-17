package io.github.ovso.righttoknow.framework.di;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.ads.InterstitialAd;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.righttoknow.framework.ad.MyAdView;

@Module public class AppModule {
  @Provides Context provideContext(Application application) {
    return application;
  }

  @Provides InterstitialAd provideInterstitialAd(Application context) {
    return MyAdView.getInterstitalAd(context);
  }
}
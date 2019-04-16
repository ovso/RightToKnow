package io.github.ovso.righttoknow.framework.di;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.righttoknow.Security;

@Module public class AppModule {
  @Provides Context provideContext(Application application) {
    return application;
  }
  @Provides InterstitialAd provideInterstitialAd(Application context) {
    InterstitialAd interstitialAd = new InterstitialAd(context);
    interstitialAd.setAdUnitId(Security.ADMOB_INTERSTITIAL_UNIT_ID.getValue());
    AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
    interstitialAd.loadAd(adRequestBuilder.build());
    return interstitialAd;
  }
}
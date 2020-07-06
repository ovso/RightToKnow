package io.github.ovso.righttoknow.framework.di

import android.app.Application
import android.content.Context
import com.google.android.gms.ads.InterstitialAd
import dagger.Module
import dagger.Provides
import io.github.ovso.righttoknow.framework.ad.MyAdView.getInterstitalAd

@Module
internal class AppModule {
  @Provides
  fun provideContext(application: Application): Context {
    return application
  }

  @Provides
  fun provideInterstitialAd(context: Application?): InterstitialAd {
    return getInterstitalAd(context)
  }
}
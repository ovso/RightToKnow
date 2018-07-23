package io.github.ovso.righttoknow.video;

import android.content.Context;
import com.google.android.gms.ads.InterstitialAd;
import dagger.Module;
import dagger.Provides;

@Module public class VideoFragmentModule {

  @Provides VideoFragmentPresenter provideVideoFragmentPresenter(VideoFragment fragment,
      InterstitialAd interstitialAd) {
    return new VideoFragmentPresenterImpl(fragment, interstitialAd);
  }
}

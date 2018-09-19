package io.github.ovso.righttoknow.ui.main.video;

import com.google.android.gms.ads.InterstitialAd;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.righttoknow.data.network.VideoRequest;
import io.github.ovso.righttoknow.utils.ResourceProvider;
import io.github.ovso.righttoknow.utils.SchedulersFacade;

@Module public class VideoFragmentModule {

  @Provides VideoFragmentPresenter provideVideoFragmentPresenter(VideoFragment fragment,
      InterstitialAd interstitialAd) {
    return new VideoFragmentPresenterImpl(fragment, interstitialAd, new VideoRequest(),
        new ResourceProvider(fragment.getContext()), new SchedulersFacade());
  }
}

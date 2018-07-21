package io.github.ovso.righttoknow.vfacilitydetail;

import com.google.android.gms.ads.InterstitialAd;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public abstract class VFacilityDetailActivityModule {
  @Singleton @Provides public static VFacilityDetailPresenter provideVFacilityDetailPresenter(
      VFacilityDetailActivity activity, InterstitialAd $interstitialAd) {
    return new VFacilityDetailPresenterImpl(activity, $interstitialAd);
  }


}

package io.github.ovso.righttoknow.vfacilitydetail;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public abstract class VFacilityDetailActivityModule {
  @Singleton @Provides public static VFacilityDetailPresenter provideVFacilityDetailPresenter(
      VFacilityDetailActivity activity) {
    return new VFacilityDetailPresenterImpl(activity);
  }
}

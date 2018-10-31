package io.github.ovso.righttoknow.ui.violation_contents;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public abstract class ViolationContentsActivityModule {
  @Singleton @Provides public static ViolationContentsPresenter provideVFacilityDetailPresenter(
      ViolationContentsActivity activity) {
    return new ViolationContentsPresenterImpl(activity);
  }
}

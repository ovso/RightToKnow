package io.github.ovso.righttoknow.ui.violator_contents;

import dagger.Module;
import dagger.Provides;
import io.github.ovso.righttoknow.data.network.model.violators.ViolatorContents;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import javax.inject.Singleton;
import org.parceler.Parcels;

@Module public abstract class ViolatorContentsActivityModule {
  @Singleton @Provides public static ViolatorContentsPresenter provideVFacilityDetailPresenter(
      ViolatorContentsActivity activity) {
    ViolatorContents contents =
        Parcels.unwrap(activity.getIntent().getParcelableExtra("contents"));
    return new ViolatorContentsPresenterImpl(activity, new SchedulersFacade(), contents);
  }
}
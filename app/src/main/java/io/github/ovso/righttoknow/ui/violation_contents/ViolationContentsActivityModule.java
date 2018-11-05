package io.github.ovso.righttoknow.ui.violation_contents;

import dagger.Module;
import dagger.Provides;
import io.github.ovso.righttoknow.data.network.model.violation.ViolationContents;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import javax.inject.Singleton;
import org.parceler.Parcels;

@Module public abstract class ViolationContentsActivityModule {
  @Singleton @Provides public static ViolationContentsPresenter provideVFacilityDetailPresenter(
      ViolationContentsActivity activity) {
    ViolationContents contents =
        Parcels.unwrap(activity.getIntent().getParcelableExtra("contents"));
    return new ViolationContentsPresenterImpl(activity, new SchedulersFacade(), contents);
  }
}
package io.github.ovso.righttoknow.ui.main;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.github.ovso.righttoknow.utils.ResourceProvider;
import io.github.ovso.righttoknow.ui.main.video.VideoFragment;
import io.github.ovso.righttoknow.ui.main.video.VideoFragmentModule;
import javax.inject.Singleton;

@Module public abstract class MainActivityModule {

  @Singleton @Provides public static MainPresenter provideMainPresenter(MainActivity activity) {
    return new MainPresenterImpl(activity, new ResourceProvider(activity));
  }

  @ContributesAndroidInjector(modules = VideoFragmentModule.class)
  abstract VideoFragment provideVideoFragmentFactory();
}
package io.github.ovso.righttoknow.main;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.github.ovso.righttoknow.video.VideoFragment;
import io.github.ovso.righttoknow.video.VideoFragmentModule;
import javax.inject.Singleton;

@Module public abstract class MainActivityModule {

  @Singleton @Provides public static MainPresenter provideMainPresenter(MainActivity activity) {
    return new MainPresenterImpl(activity);
  }

  @ContributesAndroidInjector(modules = VideoFragmentModule.class)
  abstract VideoFragment provideVideoFragmentFactory();
}
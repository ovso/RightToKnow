package io.github.ovso.righttoknow.main;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public abstract class MainActivityModule {

  @Singleton @Provides public static MainPresenter provideMainPresenter(MainActivity activity) {
    return new MainPresenterImpl(activity);
  }

}

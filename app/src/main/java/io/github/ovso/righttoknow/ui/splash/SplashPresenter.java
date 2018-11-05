package io.github.ovso.righttoknow.ui.splash;

import android.arch.lifecycle.LifecycleObserver;
import io.github.ovso.righttoknow.data.network.model.VioData;

public interface SplashPresenter extends LifecycleObserver {

  void onCreate();

  void onError(String msg);

  void onComplete(VioData vioData);

  interface View {

    void navigateToMain();
  }
}

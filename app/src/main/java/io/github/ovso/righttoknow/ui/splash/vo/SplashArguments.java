package io.github.ovso.righttoknow.ui.splash.vo;

import io.github.ovso.righttoknow.data.network.VioRequest;
import io.github.ovso.righttoknow.data.network.model.certified.VioDataWrapper;
import io.github.ovso.righttoknow.ui.base.IBuilder;
import io.github.ovso.righttoknow.ui.splash.SplashPresenter;
import io.github.ovso.righttoknow.utils.ResourceProvider;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter public class SplashArguments {

  private final VioRequest vioRequest;
  private SplashPresenter.View view;
  private ResourceProvider resourceProvider;
  private SchedulersFacade schedulers;
  private VioDataWrapper vioDataWrapper;

  private SplashArguments(Builder builder) {
    view = builder.view;
    resourceProvider = builder.resourceProvider;
    schedulers = builder.schedulers;
    vioDataWrapper = builder.vioDataWrapper;
    vioRequest = builder.vioRequest;
  }

  @Setter @Accessors(chain = true) public final static class Builder
      implements IBuilder<SplashArguments> {

    private SplashPresenter.View view;
    private ResourceProvider resourceProvider;
    private SchedulersFacade schedulers;
    private VioDataWrapper vioDataWrapper;
    private VioRequest vioRequest;

    @Override public SplashArguments build() {
      return new SplashArguments(this);
    }
  }
}
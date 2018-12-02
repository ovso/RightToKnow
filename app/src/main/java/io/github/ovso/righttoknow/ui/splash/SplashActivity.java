package io.github.ovso.righttoknow.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.network.VioRequest;
import io.github.ovso.righttoknow.data.network.model.VioData;
import io.github.ovso.righttoknow.data.network.model.certified.VioDataWrapper;
import io.github.ovso.righttoknow.ui.main.MainActivity;
import io.github.ovso.righttoknow.utils.ResourceProvider;
import io.github.ovso.righttoknow.utils.SchedulersFacade;

public class SplashActivity extends AppCompatActivity
    implements SplashPresenter.View, VioRequest.OnVioDataLoadCompleteListener {

  @BindView(R.id.progress_bar) ContentLoadingProgressBar progressBar;
  private SplashPresenter presenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);
    setupProgressBar();
    presenter = createPresenter();
    presenter.onCreate();
  }

  private void setupProgressBar() {
    progressBar.setMax(3);;
  }

  private SplashPresenter createPresenter() {
    SplashPresenter.View view = this;
    ResourceProvider rp = new ResourceProvider(this);
    VioRequest vioRequest = new VioRequest.Builder().setListener(this).build();
    getLifecycle().addObserver(vioRequest);
    SchedulersFacade schedulers = new SchedulersFacade();
    VioDataWrapper vioDataWrapper = ((App) getApplication()).getVioDataWrapper();
    SplashPresenter presenter = new SplashPresenterImpl(
        view,
        rp,
        vioRequest,
        schedulers,
        vioDataWrapper);
    getLifecycle().addObserver(presenter);
    return presenter;
  }

  @Override
  public void navigateToMain() {
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }

  @Override public void onComplete(VioData vioData) {
    presenter.onComplete(vioData);
  }

  @Override public void onError(String msg) {
    presenter.onError(msg);
  }

  @Override public void onCompleteCount(int count) {
    progressBar.setProgress(count);
  }
}
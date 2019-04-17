package io.github.ovso.righttoknow.framework;

import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.ad.MyAdView;

public abstract class BaseActivity extends DaggerAppCompatActivity {

  protected @BindView(R.id.toolbar) Toolbar toolbar;
  protected @BindView(R.id.ad_container) ViewGroup adContainer;
  private Unbinder unbinder;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResId());
    unbinder = ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    setNavigationBarColor();
    showAd();
  }

  protected void showAd() {
    adContainer.addView(MyAdView.getAdmobAdView(getApplicationContext()));
  }

  private void setNavigationBarColor() {
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }
  }

  @LayoutRes protected abstract int getLayoutResId();

  @Override protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  protected Toolbar getToolbar() {
    return toolbar;
  }
}

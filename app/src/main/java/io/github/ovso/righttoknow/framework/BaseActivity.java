package io.github.ovso.righttoknow.framework;

import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.ad.MyAdView;

public abstract class BaseActivity extends DaggerAppCompatActivity {

  private Unbinder unbinder;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResId());
    unbinder = ButterKnife.bind(this);
    final Toolbar toolbar = findViewById(R.id.toolbar);
    if (toolbar != null) {
      setSupportActionBar(toolbar);
    }

    setNavigationBarColor();
    showAd();
  }

  protected void showAd() {
    ViewGroup adContainer = findViewById(R.id.ad_container);
    if (adContainer != null) {
      adContainer.addView(MyAdView.getAdmobAdView(getApplicationContext()));
    }
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
    return findViewById(R.id.toolbar);
  }
}

package io.github.ovso.righttoknow.framework;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.gms.ads.AdView;
import dagger.android.support.DaggerAppCompatActivity;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.ad.MyAdView;

public abstract class BaseActivity extends DaggerAppCompatActivity {

  protected @BindView(R.id.toolbar) Toolbar toolbar;
  protected @BindView(R.id.ad_container) ImageView adContainer;
  private AdView admobAdView;
  private Unbinder unbinder;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    admobAdView = MyAdView.getAdmobAdView(getApplicationContext());
    setContentView(getLayoutResId());
    unbinder = ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    setNavigationBarColor();
    showAd();
  }

  protected void showAd() {
    //adContainer.addView(admobAdView);
    adContainer.setImageResource(R.drawable.banner_wink);
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

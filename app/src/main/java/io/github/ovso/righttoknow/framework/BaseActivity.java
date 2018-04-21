package io.github.ovso.righttoknow.framework;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.fsn.cauly.CaulyAdView;
import com.google.android.gms.ads.AdView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import dagger.android.support.DaggerAppCompatActivity;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.ad.MyAdView;
import io.github.ovso.righttoknow.framework.firebase.MyFirebaseRemoteConfig;
import io.github.ovso.righttoknow.framework.firebase.OnMyFirebaseRemoteConfigListener;

/**
 * Created by jaeho on 2017. 7. 31
 */

public abstract class BaseActivity extends DaggerAppCompatActivity
    implements OnMyFirebaseRemoteConfigListener {

  protected @BindView(R.id.toolbar) Toolbar toolbar;
  protected @BindView(R.id.search_view) MaterialSearchView searchView;
  protected CaulyAdView caulyAdView;
  protected AdView admobAdView;
  private Unbinder unbinder;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    caulyAdView = MyAdView.getCaulyAdView(getApplicationContext());
    admobAdView = MyAdView.getAdmobAdView(getApplicationContext());
    setContentView(getLayoutResId());
    unbinder = ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    setNavigationBarColor();

    MyFirebaseRemoteConfig config = new MyFirebaseRemoteConfig.Builder().setListener(this).build();
    config.init(this);
  }

  @DebugLog @Override public void onAdmob() {

  }

  @DebugLog @Override public void onCauly() {

  }

  @DebugLog @Override public void onFailure() {

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

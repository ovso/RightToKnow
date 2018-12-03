package io.github.ovso.righttoknow.framework;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;
import dagger.android.support.DaggerAppCompatActivity;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.ad.MyAdView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import timber.log.Timber;

public abstract class BaseActivity extends DaggerAppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.framelayout_adcontainer) FrameLayout framelayoutAdContainer;
  @BindView(R.id.imageview_othersad) ImageView imageviewOthersAd;
  private AdView admobAdView;
  private Unbinder unbinder;
  private FirebaseRemoteConfig firebaseRemoteConfig;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    admobAdView = MyAdView.getAdmobAdView(getApplicationContext());
    firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    setContentView(getLayoutResId());
    unbinder = ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    setNavigationBarColor();
    reqRemoteConfig();
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

  protected void reqRemoteConfig() {
    //int cacheExpiration = 3600;
    firebaseRemoteConfig.fetch()
        .addOnSuccessListener(this::fetchSuccess)
        .addOnFailureListener(this::fetchFailure)
        .addOnCompleteListener(this::fetchLog);
  }

  private void fetchLog(Task<Void> voidTask) {

  }

  private void fetchFailure(Exception e) {
    Timber.d(e);
    framelayoutAdContainer.addView(admobAdView);
  }

  private void fetchSuccess(Void aVoid) {
    try {
      firebaseRemoteConfig.activateFetched();
      String json = firebaseRemoteConfig.getString("ad_type");
      Timber.d("json = " + json);
      AdConfig config = new Gson().fromJson(json, AdConfig.class);
      Timber.d("config = " + config);
      switch (AdType.toType(config.type)) {
        case ADMOB:
          framelayoutAdContainer.addView(admobAdView);
          imageviewOthersAd.setVisibility(View.GONE);
          break;
        case OTHERS:
          imageviewOthersAd.setVisibility(View.VISIBLE);
          Glide.with(this).load(config.img_url).into(imageviewOthersAd);
          imageviewOthersAd.setOnClickListener(v -> {
            Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(config.nav_url));
            startActivity(intentWeb);
          });
          break;
      }
    } catch (Exception e) {
      Timber.d(e);
      framelayoutAdContainer.addView(admobAdView);
    }
  }

  @Getter @ToString static class AdConfig {
    private int type;
    private String img_url;
    private String nav_url;
  }

  @AllArgsConstructor @Getter enum AdType {
    ADMOB(0), OTHERS(1);
    private int type;

    static AdType toType(int type) {
      for (AdType adType : AdType.values()) {
        if (adType.getType() == type) {
          return adType;
        }
      }
      return ADMOB;
    }
  }
}


package io.github.ovso.righttoknow.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import javax.inject.Inject;

public abstract class AdsActivity extends BaseActivity {

  @Inject InterstitialAd interstitialAd;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    interstitialAd.setAdListener(interstitialAdadListener);
  }

  private AdListener interstitialAdadListener = new AdListener() {
    @Override public void onAdClosed() {
      super.onAdClosed();
      finish();
    }
  };

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    showInterstitialAd();
    return true;
  }

  private void showInterstitialAd() {
    if(interstitialAd.isLoaded()) {
      interstitialAd.show();
    } else {
      finish();
    }
  }

  @Override public void onBackPressed() {
    showInterstitialAd();
  }
}

package io.github.ovso.righttoknow.framework;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.Nullable;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import io.github.ovso.righttoknow.Security;

public abstract class AdsActivity2 extends Activity {

  private InterstitialAd interstitialAd;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setupInterstitialAd();
  }

  private void setupInterstitialAd() {
    interstitialAd = new InterstitialAd(this);
    interstitialAd.setAdUnitId(Security.ADMOB_INTERSTITIAL_UNIT_ID.getValue());
    AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
    interstitialAd.loadAd(adRequestBuilder.build());
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
    if (interstitialAd.isLoaded()) {
      interstitialAd.show();
    } else {
      finish();
    }
  }

  @Override public void onBackPressed() {
    showInterstitialAd();
  }
}

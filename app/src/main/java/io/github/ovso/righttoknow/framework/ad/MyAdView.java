package io.github.ovso.righttoknow.framework.ad;

import android.content.Context;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import io.github.ovso.righttoknow.Security;

public class MyAdView {

  public static AdView getAdmobAdView(Context context) {
    AdView adView = new AdView(context);
    adView.setAdSize(AdSize.SMART_BANNER);
    adView.setAdUnitId(Security.ADMOB_UNIT_ID.getValue());
    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd(adRequest);
    return adView;
  }

  public static InterstitialAd getInterstitalAd(Context context) {
    final InterstitialAd interstitialAd = new InterstitialAd(context);
    interstitialAd.setAdUnitId(Security.ADMOB_INTERSTITIAL_UNIT_ID.getValue());
    AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
    interstitialAd.loadAd(adRequestBuilder.build());
    return interstitialAd;
  }
}
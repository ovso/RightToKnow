package io.github.ovso.righttoknow.framework.ad

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import io.github.ovso.righttoknow.Security

object MyAdView {
  fun getAdmobAdView(context: Context?): AdView {
    val adView = AdView(context)
    adView.adSize = AdSize.SMART_BANNER
    adView.adUnitId = Security.ADMOB_UNIT_ID.value
    val adRequest = AdRequest.Builder().build()
    adView.loadAd(adRequest)
    return adView
  }

  @JvmStatic
  fun getInterstitalAd(context: Context): InterstitialAd {
    val interstitialAd = InterstitialAd(context)
    interstitialAd.adUnitId = Security.ADMOB_INTERSTITIAL_UNIT_ID.value
    val adRequestBuilder = AdRequest.Builder()
    interstitialAd.loadAd(adRequestBuilder.build())
    return interstitialAd
  }
}

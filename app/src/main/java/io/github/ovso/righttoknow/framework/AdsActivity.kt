package io.github.ovso.righttoknow.framework

import android.os.Bundle
import android.view.MenuItem
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.InterstitialAd
import io.github.ovso.righttoknow.framework.ad.MyAdView.getInterstitalAd

abstract class AdsActivity : BaseActivity() {
  lateinit var interstitialAd: InterstitialAd
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    interstitialAd = getInterstitalAd(this)
    interstitialAd.adListener = interstitialAdadListener
  }

  private val interstitialAdadListener: AdListener = object : AdListener() {
    override fun onAdClosed() {
      super.onAdClosed()
      finish()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    showInterstitialAd()
    return true
  }

  private fun showInterstitialAd() {
    if (interstitialAd.isLoaded) {
      interstitialAd.show()
    } else {
      finish()
    }
  }

  override fun onBackPressed() {
    showInterstitialAd()
  }
}
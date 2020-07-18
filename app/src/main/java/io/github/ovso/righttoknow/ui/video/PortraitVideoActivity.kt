package io.github.ovso.righttoknow.ui.video

import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.InterstitialAd
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.ad.MyAdView
import kotlinx.android.synthetic.main.activity_fullscreen_portrait_video.*


class PortraitVideoActivity : YouTubeBaseActivity() {
  lateinit var interstitialAd: InterstitialAd

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_fullscreen_portrait_video)
    setupAds()
    playVideo()
  }

  private fun playVideo() {
    val videoId = intent.getStringExtra("video_id")
    youtubeView.initialize("develop", object : YouTubePlayer.OnInitializedListener {
      override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer,
        wasRestored: Boolean
      ) {
        if (!wasRestored) {
          player.cueVideo(videoId)
        }
        player.setPlayerStateChangeListener(object : YouTubePlayer.PlayerStateChangeListener {
          override fun onAdStarted() {}
          override fun onLoading() {}
          override fun onVideoStarted() {}
          override fun onVideoEnded() {}
          override fun onError(p0: YouTubePlayer.ErrorReason) {}
          override fun onLoaded(videoId: String) {
            player.play()
          }
        })
      }

      override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
      ) {
        Toast.makeText(this@PortraitVideoActivity, "재생할 수 없습니다.", Toast.LENGTH_SHORT).show()
        finish()
      }
    })
  }

  private fun setupAds() {
    ad_container.addView(MyAdView.getAdmobAdView(applicationContext))
    val interstitialAdListener: AdListener = object : AdListener() {
      override fun onAdClosed() {
        super.onAdClosed()
        finish()
      }
    }

    interstitialAd = MyAdView.getInterstitalAd(this)
    interstitialAd.adListener = interstitialAdListener


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

package io.github.ovso.righttoknow.ui.video

import android.os.Bundle
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import io.github.ovso.righttoknow.databinding.ActivityFullscreenPortraitVideoBinding
import io.github.ovso.righttoknow.exts.viewBinding
import io.github.ovso.righttoknow.framework.AdsActivity


class PortraitVideoActivity : AdsActivity() {
  private val binding by viewBinding(ActivityFullscreenPortraitVideoBinding::inflate)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    lifecycle.addObserver(binding.youtubeFullscreenPortrait)
    binding.youtubeFullscreenPortrait.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
      override fun onYouTubePlayer(youTubePlayer: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer) {
        intent.getStringExtra("video_id")?.let { videoId ->
          youTubePlayer.cueVideo(videoId, 0F)
        }

      }
    })
/*
    if (intent.hasExtra("video_id")) {
      val youTubePlayerFragment = supportFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerFragment
      youTubePlayerFragment.initialize(Security.GOOGLE_API_KEY.value,
        object : YouTubePlayer.OnInitializedListener {
          override fun onInitializationSuccess(provider: YouTubePlayer.Provider,
                                               youTubePlayer: YouTubePlayer, b: Boolean) {
            youTubePlayer.loadVideo(intent.getStringExtra("video_id"))
          }

          override fun onInitializationFailure(provider: YouTubePlayer.Provider,
                                               youTubeInitializationResult: YouTubeInitializationResult) {
          }
        })
    } else {
      Toast.makeText(this, R.string.no_videos_found, Toast.LENGTH_SHORT).show()
      finish()
    }
*/
  }
}

package io.github.ovso.righttoknow.ui.video

import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.Security
import io.github.ovso.righttoknow.databinding.ActivityFullscreenVideoBinding
import io.github.ovso.righttoknow.exts.viewBinding
import io.github.ovso.righttoknow.framework.AdsActivity

class LandscapeVideoActivity : AdsActivity() {
  private val binding by viewBinding(ActivityFullscreenVideoBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

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
  }
}

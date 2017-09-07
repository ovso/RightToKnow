package io.github.ovso.righttoknow.videodetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.main.BaseActivity;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoDetailActivity extends BaseActivity {
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    YouTubePlayerSupportFragment youTubePlayerSupportFragment =
        (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(
            R.id.youtube_fragment);
    youTubePlayerSupportFragment.initialize("AIzaSyBdY9vP4_vQs5YEGJ3Ghu6s5gGY8yFlo0s",
        new YouTubePlayer.OnInitializedListener() {
          @Override public void onInitializationSuccess(YouTubePlayer.Provider provider,
              YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayer.cueVideo("PuaYhnGmeEk");
          }

          @Override public void onInitializationFailure(YouTubePlayer.Provider provider,
              YouTubeInitializationResult youTubeInitializationResult) {
          }
        });
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_video_detail;
  }
}

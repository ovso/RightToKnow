package io.github.ovso.righttoknow.videodetail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.Security;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoDetailActivity extends Activity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_detail);
    ButterKnife.bind(this);
    if (getIntent().hasExtra("video_id")) {
      YouTubePlayerFragment youTubePlayerFragment =
          (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
      youTubePlayerFragment.initialize(Security.GOOGLE_API_KEY.getValue(),
          new YouTubePlayer.OnInitializedListener() {
            @Override public void onInitializationSuccess(YouTubePlayer.Provider provider,
                YouTubePlayer youTubePlayer, boolean b) {
              youTubePlayer.loadVideo(getIntent().getStringExtra("video_id"));
            }

            @Override public void onInitializationFailure(YouTubePlayer.Provider provider,
                YouTubeInitializationResult youTubeInitializationResult) {
            }
          });
    } else {
      Toast.makeText(this, R.string.no_videos_found, Toast.LENGTH_SHORT).show();
      onBackPressed();
    }
  }
}

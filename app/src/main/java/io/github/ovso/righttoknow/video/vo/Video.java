package io.github.ovso.righttoknow.video.vo;

import com.google.firebase.database.IgnoreExtraProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 9. 7
 */

@Getter @IgnoreExtraProperties @ToString @EqualsAndHashCode(callSuper = false) public class Video {
  private String title;
  private String date;
  private String url;
  private String video_id;
}

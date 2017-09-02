package io.github.ovso.righttoknow.news.vo;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 9. 1
 */

@Getter @IgnoreExtraProperties @ToString @EqualsAndHashCode(callSuper = false) public class News
    implements Serializable {
  private String title;
  private String date;
  private String url;
}

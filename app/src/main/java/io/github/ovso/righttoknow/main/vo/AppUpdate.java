package io.github.ovso.righttoknow.main.vo;

import com.google.firebase.database.IgnoreExtraProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 9. 17
 */

@Getter @IgnoreExtraProperties @ToString @EqualsAndHashCode(callSuper = false)
public class AppUpdate {
  private String store_version;
  private boolean force_update;
  private String message;
}
package io.github.ovso.righttoknow.certified.vo;

import com.google.firebase.database.IgnoreExtraProperties;
import lombok.Getter;

/**
 * Created by jaeho on 2017. 8. 21
 */

@Getter @IgnoreExtraProperties public class ChildCertified {
  private String title;
  private String pdf_name;
}

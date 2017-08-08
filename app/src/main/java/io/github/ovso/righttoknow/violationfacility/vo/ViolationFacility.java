package io.github.ovso.righttoknow.violationfacility.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 8. 1
 */

@Data @ToString @EqualsAndHashCode(callSuper = false) public class ViolationFacility {
  private String turn;
  private String sido;
  private String sigungu;
  private String name;
  private String type;
  private String boss;
  private String director;
  private String address;
  private String link;
}

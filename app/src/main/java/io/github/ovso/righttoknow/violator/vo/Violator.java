package io.github.ovso.righttoknow.violator.vo;

import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 8. 3
 */

@Data @ToString @EqualsAndHashCode(callSuper = false) public class Violator
    extends ViolationFacility {
  private String violator;
  private int history;
}
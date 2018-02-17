package io.github.ovso.righttoknow.violator.vo;

import io.github.ovso.righttoknow.violationfacility.model.ViolationFacility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 8. 3
 */

@Data @ToString @EqualsAndHashCode(callSuper = false) public class Violator
    extends ViolationFacility {
  private String name;
  private int history;
}
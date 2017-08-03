package io.github.ovso.righttoknow.violator.vo;

import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
import lombok.Data;

/**
 * Created by jaeho on 2017. 8. 3
 */

@Data public class Violator extends ViolationFacility {
  private String violator;
  private String history;
}

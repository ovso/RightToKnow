package io.github.ovso.righttoknow.vfacilitydetail.vo;

import io.github.ovso.righttoknow.violationfacility.model.ViolationFacility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 8. 2
 */

@Data @ToString @EqualsAndHashCode(callSuper = false) public class VFacilityDetail extends ViolationFacility {
  private String oldName;
  private String oldBoss;
  private String oldDirector;
  //private String action;
  //private String disposal;
  private String tbody;
  private String violatorName;
  private String history;
}

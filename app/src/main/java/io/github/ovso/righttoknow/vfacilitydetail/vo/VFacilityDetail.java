package io.github.ovso.righttoknow.vfacilitydetail.vo;

import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
import lombok.Data;

/**
 * Created by jaeho on 2017. 8. 2
 */

@Data public class VFacilityDetail extends ViolationFacility {
  private String oldName;
  private String oldBoss;
  private String oldDirector;
  private String action;
  private String disposal;
  private String tbody;
  private String violatorName;
  private String history;
}

package io.github.ovso.righttoknow.violationfacility.model;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 8. 1
 */

@Getter @ToString @EqualsAndHashCode(callSuper = false) public class ViolationFacility2
    implements Serializable {
  private int reg_number;
  private String sido;
  private String sigungu;
  private String type;
  private String now_master;
  private String now_director;
  private String now_fac_name;
  private String old_master;
  private String old_director;
  private String old_fac_name;
  private String address;
  private String action;
  private String disposal;
}

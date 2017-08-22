package io.github.ovso.righttoknow.violationfacility.vo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 8. 1
 */

@Data @ToString @EqualsAndHashCode(callSuper = false) public class ViolationFacility
    implements Serializable {
  private int reg_number;
  private String sido;
  private String sigungu;
  private String type;
  private String now_boss;
  private String now_director;
  private String now_fac_name;
  private String old_boss;
  private String old_director;
  private String old_fac_name;
  private String address;
  private List<String> action;
  private List<String> disposal;
}

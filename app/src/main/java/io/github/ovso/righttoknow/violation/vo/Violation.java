package io.github.ovso.righttoknow.violation.vo;

import lombok.Data;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 8. 1
 */

@Data @ToString public class Violation {
  private int turn;
  private String sido;
  private String sigungu;
  private String name;
  private String type;
  private String boss;
  private String director;
  private String address;
}
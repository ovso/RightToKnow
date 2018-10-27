package io.github.ovso.righttoknow.data.network.model.violation;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties public class Violation {
  public String order;
  public String sido;
  public String sigungu;
  public String type;
  public String master;
  public String director;
  public String address;
  public String link;
  public String vio_ccc;
  public ViolationContents contents;
}

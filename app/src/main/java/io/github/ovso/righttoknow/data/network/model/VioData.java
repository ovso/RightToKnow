package io.github.ovso.righttoknow.data.network.model;

import io.github.ovso.righttoknow.data.network.model.certified.CertifiedData;
import io.github.ovso.righttoknow.data.network.model.violation.ViolationData;
import io.github.ovso.righttoknow.data.network.model.violators.ViolatorData;

public class VioData {
  public String date;
  public ViolatorData violator;
  public ViolationData violation;
  public CertifiedData certified;
}

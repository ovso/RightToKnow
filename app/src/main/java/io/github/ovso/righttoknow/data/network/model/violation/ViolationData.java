package io.github.ovso.righttoknow.data.network.model.violation;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.List;

@IgnoreExtraProperties public class ViolationData {
  public String date;
  public List<Violation> items;
}

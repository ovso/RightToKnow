package io.github.ovso.righttoknow.certified.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import lombok.Getter;

/**
 * Created by jaeho on 2017. 8. 21
 */

@Getter @IgnoreExtraProperties public class ChildCertified {
  private String title;
  private String pdf_name;

  public static List<ChildCertified> convertToItems(DataSnapshot dataSnapshot) {
    final Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
    final List<ChildCertified> items = new ArrayList<>();
    while (iterator.hasNext()) {
      ChildCertified c = iterator.next().getValue(ChildCertified.class);
      items.add(c);
    }

    return sorted(items);
  }

  private static List<ChildCertified> sorted(List<ChildCertified> items) {
    Comparator<ChildCertified> comparator =
        (t1, t2) -> t2.getPdf_name().compareTo(t1.getPdf_name());
    Collections.sort(items, comparator);
    return items;
  }
}

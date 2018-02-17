package io.github.ovso.righttoknow.violator.model;

import com.google.firebase.database.DataSnapshot;
import io.github.ovso.righttoknow.common.SearchUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 8. 3
 */

@Getter @ToString @EqualsAndHashCode(callSuper = false) public class Violator implements
    Serializable {
  private String name;
  private int history;
  private int reg_number;
  private String sido;
  private String sigungu;
  private String old_fac_name;
  private String address;
  private List<String> action;
  private List<String> disposal;

  public static ArrayList<Violator> convertToItems(DataSnapshot dataSnapshot) {
    final ArrayList<Violator> items = new ArrayList<>();
    Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
    while (iterator.hasNext()) {
      Violator v = iterator.next().getValue(Violator.class);
      items.add(v);
    }
    return items;
  }

  public static ArrayList<Violator> getSearchResultItems(ArrayList<Violator> originItems,
      String query) {
    ArrayList<Violator> items = new ArrayList<>();
    for (Violator originItem : originItems) {
      if (SearchUtils.isListContains(originItem.getAction(), query)
          || SearchUtils.isListContains(originItem.getDisposal(), query)
          || SearchUtils.isTextContains(originItem.getAddress(), query)
          || SearchUtils.isTextContains(originItem.getName(), query)
          || SearchUtils.isTextContains(originItem.getOld_fac_name(), query)) {
        items.add(originItem);
      }
    }
    return items;
  }
}
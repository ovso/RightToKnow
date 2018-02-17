package io.github.ovso.righttoknow.violationfacility.model;

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
 * Created by jaeho on 2017. 8. 1
 */

@Getter @ToString @EqualsAndHashCode(callSuper = false) public class ViolationFacility
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

  public static ArrayList<ViolationFacility> convertToItems(DataSnapshot dataSnapshot) {
    final ArrayList<ViolationFacility> items = new ArrayList<>();
    Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
    while (iterator.hasNext()) {
      ViolationFacility v = iterator.next().getValue(ViolationFacility.class);
      items.add(v);
    }
    return items;
  }

  public static ArrayList<ViolationFacility> getSearchResultItems(
      ArrayList<ViolationFacility> originItems, String query) {
    ArrayList<ViolationFacility> items = new ArrayList<>();
    for (ViolationFacility originItem : originItems) {
      if (SearchUtils.isListContains(originItem.getAction(), query)
          || SearchUtils.isListContains(originItem.getDisposal(), query)
          || SearchUtils.isTextContains(originItem.getAddress(), query)
          || SearchUtils.isTextContains(originItem.getOld_boss(), query)
          || SearchUtils.isTextContains(originItem.getNow_boss(), query)
          || SearchUtils.isTextContains(originItem.getOld_director(), query)
          || SearchUtils.isTextContains(originItem.getNow_director(), query)
          || SearchUtils.isTextContains(originItem.getOld_fac_name(), query)
          || SearchUtils.isTextContains(originItem.getNow_fac_name(), query)
          || SearchUtils.isTextContains(originItem.getType(), query)) {
        items.add(originItem);
      }
    }
    return items;
  }
}

package io.github.ovso.righttoknow.violationfacility;

import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;

/**
 * Created by jaeho on 2017. 8. 8
 */

public interface FacilityAdapterDataModel<K> extends BaseAdapterDataModel<ViolationFacility> {
  void sortTurn();

  void sortSido();

  void sortType();
}

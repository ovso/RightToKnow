package io.github.ovso.righttoknow.violationfacility;

import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
import javax.annotation.Nonnull;

/**
 * Created by jaeho on 2017. 8. 10..
 */

public interface FacilityAdapterDataModel<V> extends BaseAdapterDataModel<ViolationFacility> {
  void searchMyLocation(String locality, String subLocality);
  void searchAllWords(@Nonnull String query);
}

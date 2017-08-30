package io.github.ovso.righttoknow.violator;

import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.violator.vo.Violator;
import javax.annotation.Nonnull;

/**
 * Created by jaeho on 2017. 8. 10
 */

public interface ViolatorAdapterDataModel<V> extends BaseAdapterDataModel<Violator> {
  void searchMyLocation(String locality, String subLocality);
  void searchAllWords(@Nonnull String query);
}

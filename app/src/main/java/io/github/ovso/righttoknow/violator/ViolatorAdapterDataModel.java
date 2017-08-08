package io.github.ovso.righttoknow.violator;

import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.violator.vo.Violator;

/**
 * Created by jaeho on 2017. 8. 8
 */

public interface ViolatorAdapterDataModel<K> extends BaseAdapterDataModel<Violator> {
  void sortTurn();
  void sortSido();
  void sortHistory();
}

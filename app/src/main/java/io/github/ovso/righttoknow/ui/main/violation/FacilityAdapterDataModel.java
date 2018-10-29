package io.github.ovso.righttoknow.ui.main.violation;

import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.ui.main.violation.model.VioFac;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 10..
 */

public interface FacilityAdapterDataModel<V> extends BaseAdapterDataModel<VioFac> {
  List<VioFac> getAll();
}

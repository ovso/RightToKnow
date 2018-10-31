package io.github.ovso.righttoknow.ui.main.violation;

import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.ui.main.violation.model.VioFac;
import java.util.List;

public interface ViolationAdapterDataModel<V> extends BaseAdapterDataModel<VioFac> {
  List<VioFac> getAll();
}

package io.github.ovso.righttoknow.ui.main.video;

import io.github.ovso.righttoknow.data.network.model.video.SearchItem;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;

public interface VideoAdapterDataModel extends BaseAdapterDataModel<SearchItem> {
  void setOnItemClickListener(OnRecyclerItemClickListener<SearchItem> listener);
}

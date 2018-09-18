package io.github.ovso.righttoknow.ui.main.video;

import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.ui.main.video.model.Video;

public interface VideoAdapterDataModel extends BaseAdapterDataModel<Video> {
  void setOnItemClickListener(OnRecyclerItemClickListener<Video> listener);
}

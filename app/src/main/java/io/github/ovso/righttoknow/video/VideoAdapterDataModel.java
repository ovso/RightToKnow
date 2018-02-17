package io.github.ovso.righttoknow.video;

import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.video.model.Video;

/**
 * Created by jaeho on 2017. 9. 2
 */

public interface VideoAdapterDataModel extends BaseAdapterDataModel<Video> {
  void setOnItemClickListener(OnRecyclerItemClickListener<Video> listener);
}

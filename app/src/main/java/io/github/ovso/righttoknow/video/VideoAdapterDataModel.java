package io.github.ovso.righttoknow.video;

import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.video.vo.Video;

/**
 * Created by jaeho on 2017. 9. 2
 */

public interface VideoAdapterDataModel extends BaseAdapterDataModel<Video> {
  void setOnItemClickListener(OnRecyclerItemClickListener<Video> listener);
  void sort();
}

package io.github.ovso.righttoknow.video;

import android.os.Bundle;
import io.github.ovso.righttoknow.video.vo.Video;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoFragmentPresenterImpl implements VideoFragmentPresenter {

  private VideoFragmentPresenter.View view;
  private VideoAdapterDataModel adapterDataModel;
  VideoFragmentPresenterImpl(VideoFragmentPresenter.View view) {
    this.view = view;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setRecyclerView();
    Video video = new Video("zzzz","7kD0ZYzJbYo","20" );
    adapterDataModel.add(video);
    adapterDataModel.add(video);
    adapterDataModel.add(video);
    adapterDataModel.add(video);
    adapterDataModel.add(video);
    adapterDataModel.add(video);
    adapterDataModel.add(video);

    view.refresh();

    adapterDataModel.setOnItemClickListener(item -> {
      view.navigateToVideoDetail();
    });
  }

  @Override public void setAdapterDataModel(VideoAdapterDataModel dataModel) {
    this.adapterDataModel = dataModel;
  }
}
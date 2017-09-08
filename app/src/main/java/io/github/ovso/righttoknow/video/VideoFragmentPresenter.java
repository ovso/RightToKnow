package io.github.ovso.righttoknow.video;

import android.os.Bundle;
import io.github.ovso.righttoknow.video.vo.Video;

/**
 * Created by jaeho on 2017. 9. 7
 */

public interface VideoFragmentPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterDataModel(VideoAdapterDataModel dataModel);

  void onRefresh();

  void onDestroyView();

  interface View {

    void setRecyclerView();

    void refresh();

    void navigateToVideoDetail(Video video);

    void setRefreshLayout();

    void showLoading();

    void hideLoading();
  }
}
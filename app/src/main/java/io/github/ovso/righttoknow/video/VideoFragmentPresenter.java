package io.github.ovso.righttoknow.video;

import android.os.Bundle;

/**
 * Created by jaeho on 2017. 9. 7
 */

public interface VideoFragmentPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterDataModel(VideoAdapterDataModel dataModel);

  interface View {

    void setRecyclerView();

    void refresh();

    void navigateToVideoDetail();
  }
}
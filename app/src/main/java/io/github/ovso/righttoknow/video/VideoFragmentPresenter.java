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

  boolean onOptionsItemSelected(int itemId);

  void onCreateOptionsMenu();

  interface View {

    void setRecyclerView();

    void refresh();

    void navigateToVideoDetail(Video video);

    void setRefreshLayout();

    void showLoading();

    void hideLoading();

    void setHasOptionsMenu(boolean hasMenu);

    void setLandscapeMode();

    void setPortraitMode();

    void clearMenuMode();

    void showWarningDialog();
  }
}
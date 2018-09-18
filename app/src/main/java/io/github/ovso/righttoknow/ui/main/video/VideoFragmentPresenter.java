package io.github.ovso.righttoknow.ui.main.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.ui.main.video.model.Video;

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

  void onActivityResult(int requestCode, int resultCode, Intent data);

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

    void showMessage(@StringRes int resId);
  }
}
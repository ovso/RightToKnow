package io.github.ovso.righttoknow.ui.main.video;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.data.network.model.video.SearchItem;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;

public interface VideoFragmentPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterDataModel(BaseAdapterDataModel<SearchItem> dataModel);

  void onRefresh();

  void onDestroyView();

  void onActivityResult(int requestCode, int resultCode, Intent data);

  void onLoadMore();

  void onItemClick(SearchItem data);

  interface View {

    void setRecyclerView();

    void refresh();

    void setRefreshLayout();

    void showLoading();

    void hideLoading();

    void showMessage(@StringRes int resId);

    void setLoaded();

    void showPortraitVideo(String videoId);

    void showLandscapeVideo(String videoId);

    void showYoutubeUseWarningDialog();

    void showVideoTypeDialog(DialogInterface.OnClickListener onClickListener);
  }
}
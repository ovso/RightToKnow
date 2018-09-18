package io.github.ovso.righttoknow.ui.main.video;

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

  boolean onOptionsItemSelected(int itemId);

  void onCreateOptionsMenu();

  void onActivityResult(int requestCode, int resultCode, Intent data);

  void onLoadMore();

  void onItemClick(SearchItem data);

  interface View {

    void setRecyclerView();

    void refresh();

    void navigateToVideoDetail(SearchItem video);

    void setRefreshLayout();

    void showLoading();

    void hideLoading();

    void setHasOptionsMenu(boolean hasMenu);

    void setLandscapeMode();

    void setPortraitMode();

    void clearMenuMode();

    void showWarningDialog();

    void showMessage(@StringRes int resId);

    void setLoaded();
  }
}
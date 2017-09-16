package io.github.ovso.righttoknow.video;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
import io.github.ovso.righttoknow.video.vo.Video;
import java.util.List;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoFragmentPresenterImpl implements VideoFragmentPresenter {

  private VideoFragmentPresenter.View view;
  private VideoAdapterDataModel adapterDataModel;
  private VideoInteractor interactor;

  VideoFragmentPresenterImpl(VideoFragmentPresenter.View view) {
    this.view = view;
    interactor = new VideoInteractor();
    interactor.setOnChildResultListener(onChildResultListener);
  }

  private OnChildResultListener onChildResultListener = new OnChildResultListener<List<Video>>() {
    @Override public void onPre() {
      view.showLoading();
    }

    @Override public void onResult(List<Video> results) {
      adapterDataModel.clear();
      adapterDataModel.addAll(results);
      adapterDataModel.sort();
      view.refresh();
    }

    @Override public void onPost() {
      view.hideLoading();
    }

    @Override public void onError() {

    }
  };

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setHasOptionsMenu(true);
    view.setRefreshLayout();
    view.setRecyclerView();
    interactor.req();
    adapterDataModel.setOnItemClickListener(item -> {
      try {
        view.navigateToVideoDetail(item);
      } catch (ActivityNotFoundException e) {
        e.printStackTrace();
        view.showWarningDialog();
      }
    });
  }

  @Override public void setAdapterDataModel(VideoAdapterDataModel dataModel) {
    this.adapterDataModel = dataModel;
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    interactor.req();
  }

  @Override public void onDestroyView() {
    interactor.cancel();
  }

  @DebugLog @Override public boolean onOptionsItemSelected(int itemId) {
    view.clearMenuMode();
    switch (itemId) {
      case R.id.option_menu_lock_portrait:
        view.setLandscapeMode();
        break;
      case R.id.option_menu_lock_landscape:
        view.setPortraitMode();
        break;
    }
    return true;
  }

  @Override public void onCreateOptionsMenu() {
    view.setPortraitMode();
  }
}
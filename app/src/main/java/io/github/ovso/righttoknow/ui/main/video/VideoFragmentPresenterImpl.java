package io.github.ovso.righttoknow.ui.main.video;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.ads.InterstitialAd;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.ActivityReqCode;
import io.github.ovso.righttoknow.data.VideoMode;
import io.github.ovso.righttoknow.data.network.VideoRequest;
import io.github.ovso.righttoknow.data.network.model.video.SearchItem;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.utils.ResourceProvider;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.List;
import timber.log.Timber;

public class VideoFragmentPresenterImpl implements VideoFragmentPresenter {

  private VideoFragmentPresenter.View view;
  private BaseAdapterDataModel<SearchItem> adapterDataModel;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private InterstitialAd interstitialAd;
  private VideoRequest videoRequest;
  private ResourceProvider resourceProvider;
  private String pageToken;
  private String q;
  private SchedulersFacade schedulersFacade;

  VideoFragmentPresenterImpl(VideoFragmentPresenter.View view, InterstitialAd $inInterstitialAd,
      VideoRequest $videoRequest, ResourceProvider $resourceProvider,
      SchedulersFacade $schSchedulersFacade) {
    this.view = view;
    interstitialAd = $inInterstitialAd;
    videoRequest = $videoRequest;
    resourceProvider = $resourceProvider;
    schedulersFacade = $schSchedulersFacade;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setRefreshLayout();
    view.setRecyclerView();
    req();
  }

  private void req() {
    view.showLoading();
    q = resourceProvider.getString(R.string.video_query);
    Disposable disposable = videoRequest.getResult(q, pageToken)
        .subscribeOn(schedulersFacade.io())
        .observeOn(schedulersFacade.ui())
        .subscribe(search -> {
          pageToken = search.getNextPageToken();
          adapterDataModel.addAll(search.getItems());
          view.refresh();
          view.hideLoading();
        }, throwable -> view.hideLoading());
    compositeDisposable.add(disposable);
  }

  @Override public void setAdapterDataModel(BaseAdapterDataModel<SearchItem> dataModel) {
    this.adapterDataModel = dataModel;
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    pageToken = null;
    req();
  }

  @Override public void onDestroyView() {
    compositeDisposable.dispose();
    compositeDisposable.clear();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == ActivityReqCode.YOUTUBE.get() && interstitialAd.isLoaded()) {
      interstitialAd.show();
    }
  }

  @Override public void onLoadMore() {

    if (!TextUtils.isEmpty(pageToken) && !TextUtils.isEmpty(q)) {
      Disposable disposable = videoRequest.getResult(q, pageToken)
          .subscribeOn(schedulersFacade.io())
          .observeOn(schedulersFacade.ui())
          .subscribe(
              search -> {
                pageToken = search.getNextPageToken();
                List<SearchItem> items = search.getItems();
                adapterDataModel.addAll(items);
                view.refresh();
                view.setLoaded();
              }, throwable -> {
                Timber.d(throwable);
              });
      compositeDisposable.add(disposable);
    }
  }

  @Override public void onItemClick(SearchItem data) {
    final DialogInterface.OnClickListener onClickListener = (dialog, which) -> {
      dialog.dismiss();

      try {
        dialog.dismiss();
        String videoId = data.getId().getVideoId();
        switch (VideoMode.toMode(which)) {
          case PORTRAIT:
            view.showPortraitVideo(videoId);
            break;
          case LANDSCAPE:
            view.showLandscapeVideo(videoId);
            break;
          case CANCEL:
            break;
        }
      } catch (ActivityNotFoundException e) {
        e.printStackTrace();
        view.showYoutubeUseWarningDialog();
      }
    };
    view.showVideoTypeDialog(onClickListener);
  }
}
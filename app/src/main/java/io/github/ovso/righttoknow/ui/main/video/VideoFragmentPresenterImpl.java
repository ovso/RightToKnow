package io.github.ovso.righttoknow.ui.main.video;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.ActivityReqCode;
import io.github.ovso.righttoknow.data.network.VideoRequest;
import io.github.ovso.righttoknow.data.network.model.video.Search;
import io.github.ovso.righttoknow.data.network.model.video.SearchItem;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.utils.ResourceProvider;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;
import timber.log.Timber;

public class VideoFragmentPresenterImpl implements VideoFragmentPresenter {

  private VideoFragmentPresenter.View view;
  private VideoAdapterDataModel adapterDataModel;
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
    view.setHasOptionsMenu(true);
    view.setRefreshLayout();
    view.setRecyclerView();
    setAdapterListener();
    req();
  }

  private void req() {
    q = resourceProvider.getString(R.string.video_query);
    Disposable disposable = videoRequest.getResult(q, pageToken)
        .subscribeOn(schedulersFacade.io())
        .observeOn(schedulersFacade.ui())
        .subscribe(new Consumer<Search>() {
          @Override public void accept(Search search) throws Exception {
            pageToken = search.getNextPageToken();
            adapterDataModel.addAll(search.getItems());
            view.refresh();
            view.hideLoading();
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {

          }
        });
    compositeDisposable.add(disposable);
  }

  private void setAdapterListener() {
    adapterDataModel.setOnItemClickListener(new OnRecyclerItemClickListener<SearchItem>() {
      @Override public void onItemClick(SearchItem item) {
        navigateToVideoDetail(item);
      }
    });
  }

  private void navigateToVideoDetail(SearchItem item) {
    try {
      view.navigateToVideoDetail(item);
    } catch (ActivityNotFoundException e) {
      e.printStackTrace();
      view.showWarningDialog();
    }
  }

  @Override public void setAdapterDataModel(VideoAdapterDataModel dataModel) {
    this.adapterDataModel = dataModel;
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    req();
  }

  @Override public void onDestroyView() {
    compositeDisposable.dispose();
    compositeDisposable.clear();
  }

  @Override public boolean onOptionsItemSelected(int itemId) {
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

  }
}
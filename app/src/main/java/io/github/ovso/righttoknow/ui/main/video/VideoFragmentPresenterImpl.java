package io.github.ovso.righttoknow.ui.main.video;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.ActivityReqCode;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.ui.main.video.model.Video;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class VideoFragmentPresenterImpl implements VideoFragmentPresenter {

  private VideoFragmentPresenter.View view;
  private VideoAdapterDataModel adapterDataModel;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private DatabaseReference databaseReference =
      FirebaseDatabase.getInstance().getReference().child("child_care_video");
  private InterstitialAd interstitialAd;
  //private Video videoItem;

  VideoFragmentPresenterImpl(VideoFragmentPresenter.View view, InterstitialAd $inInterstitialAd) {
    this.view = view;
    interstitialAd = $inInterstitialAd;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setHasOptionsMenu(true);
    view.setRefreshLayout();
    view.setRecyclerView();
    setAdapterListener();
    req();
  }

  private void setAdapterListener() {
    adapterDataModel.setOnItemClickListener(new OnRecyclerItemClickListener<Video>() {
      @Override public void onItemClick(Video item) {
        //videoItem = item;
        //if (interstitialAd.isLoaded()) {
        //  interstitialAd.show();
        //} else {
          navigateToVideoDetail(item);
        //}
      }
    });
  }

  private void navigateToVideoDetail(Video item) {
    try {
      //videoItem = item;
      view.navigateToVideoDetail(item);
    } catch (ActivityNotFoundException e) {
      e.printStackTrace();
      view.showWarningDialog();
    }
  }

  private void req() {
    view.showLoading();
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .map(new Function<DataSnapshot, List<Video>>() {
          @Override public List<Video> apply(DataSnapshot dataSnapshot) throws Exception {
            return Video.convertToItems(dataSnapshot);
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Video>>() {
          @Override public void accept(List<Video> items) throws Exception {
            adapterDataModel.addAll(items);
            view.refresh();
            view.hideLoading();
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            view.showMessage(R.string.error_server);
            view.hideLoading();
          }
        }));
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
    if(requestCode == ActivityReqCode.YOUTUBE.get() && interstitialAd.isLoaded()) {
      interstitialAd.show();
    }
  }
}
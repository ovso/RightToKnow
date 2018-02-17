package io.github.ovso.righttoknow.video;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.video.model.Video;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoFragmentPresenterImpl implements VideoFragmentPresenter {

  private VideoFragmentPresenter.View view;
  private VideoAdapterDataModel adapterDataModel;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private DatabaseReference databaseReference =
      FirebaseDatabase.getInstance().getReference().child("child_care_video");

  VideoFragmentPresenterImpl(VideoFragmentPresenter.View view) {
    this.view = view;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setHasOptionsMenu(true);
    view.setRefreshLayout();
    view.setRecyclerView();
    adapterDataModel.setOnItemClickListener(item -> {
      try {
        view.navigateToVideoDetail(item);
      } catch (ActivityNotFoundException e) {
        e.printStackTrace();
        view.showWarningDialog();
      }
    });
    req();
  }

  private void req() {
    view.showLoading();
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .map(dataSnapshot -> Video.convertToItems(dataSnapshot))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          view.showMessage(R.string.error_server);
          view.hideLoading();
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
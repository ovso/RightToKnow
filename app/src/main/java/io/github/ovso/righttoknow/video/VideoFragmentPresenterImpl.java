package io.github.ovso.righttoknow.video;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.video.model.Video;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

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
    adapterDataModel.setOnItemClickListener(new OnRecyclerItemClickListener<Video>() {
      @Override public void onItemClick(Video item) {
        try {
          view.navigateToVideoDetail(item);
        } catch (ActivityNotFoundException e) {
          e.printStackTrace();
          view.showWarningDialog();
        }
      }
    });
    req();
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
}
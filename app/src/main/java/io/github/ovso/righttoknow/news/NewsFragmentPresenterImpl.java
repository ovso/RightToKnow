package io.github.ovso.righttoknow.news;

import android.os.Bundle;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
import io.github.ovso.righttoknow.news.vo.News;
import java.util.List;

/**
 * Created by jaeho on 2017. 9. 1
 */

public class NewsFragmentPresenterImpl implements NewsFragmentPresenter {

  private NewsFragmentPresenter.View view;
  private NewsInteractor interactor;
  NewsFragmentPresenterImpl(NewsFragmentPresenter.View view) {
    this.view = view;
    interactor = new NewsInteractor();
    interactor.setOnChildResultListener(onChildResultListener);

  }

  private OnChildResultListener<List<News>> onChildResultListener = new OnChildResultListener<List<News>>() {
    @DebugLog @Override public void onPre() {
      view.showLoading();
    }

    @DebugLog @Override public void onResult(List<News> result) {
      adapterDataModel.clear();
      adapterDataModel.addAll(result);

      view.refresh();
    }

    @DebugLog @Override public void onPost() {
      view.hideLoading();
    }

    @DebugLog @Override public void onError() {
      view.hideLoading();
    }
  };
  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    adapterDataModel.setOnItemClickListener(onRecyclerItemClickListener);
    interactor.req();
  }
  private OnRecyclerItemClickListener onRecyclerItemClickListener =
      (OnRecyclerItemClickListener<News>) item -> {

      };
  private NewsAdapterDataModel adapterDataModel;
  @Override public void setAdapterModel(NewsAdapterDataModel dataModel) {
    adapterDataModel = dataModel;
  }

  @Override public void onRefresh() {
    interactor.req();
  }
}

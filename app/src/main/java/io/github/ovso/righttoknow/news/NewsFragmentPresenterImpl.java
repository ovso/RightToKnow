package io.github.ovso.righttoknow.news;

import android.os.Bundle;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.news.model.News;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jaeho on 2017. 9. 1
 */

public class NewsFragmentPresenterImpl implements NewsFragmentPresenter {

  private NewsFragmentPresenter.View view;
  private NewsNetwork network;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  NewsFragmentPresenterImpl(NewsFragmentPresenter.View view) {
    this.view = view;
    network = new NewsNetwork(MyApplication.getInstance().getApplicationContext(),
        "https://openapi.naver.com");
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    adapterDataModel.setOnItemClickListener(onRecyclerItemClickListener);

    req();
  }

  private void req() {
    compositeDisposable.add(network.getNews()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(result -> {
          adapterDataModel.clear();
          adapterDataModel.addAll(result.getItems());
          view.refresh();
          view.hideLoading();
        }, throwable -> view.hideLoading()));
  }

  private OnNewsRecyclerItemClickListener onRecyclerItemClickListener = new OnNewsRecyclerItemClickListener<News>() {
    @Override public void onSimpleNewsItemClick(News item) {
      view.showSimpleNewsDialog(item);
    }

    @Override public void onItemClick(News item) {
      view.navigateToDetailNews(item);
    }

  };
  private NewsAdapterDataModel adapterDataModel;

  @Override public void setAdapterModel(NewsAdapterDataModel dataModel) {
    adapterDataModel = dataModel;
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    req();
  }
}

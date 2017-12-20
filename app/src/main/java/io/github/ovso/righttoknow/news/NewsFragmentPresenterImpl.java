package io.github.ovso.righttoknow.news;

import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.news.model.News;
import io.github.ovso.righttoknow.news.model.NewsResult;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

  private Disposable disposable;

  private void req() {
    view.showLoading();

    Single<NewsResult> newsResultAbuse =
        network.getNews(R.string.api_query_abuse).subscribeOn(Schedulers.io());

    Single<NewsResult> newsResultMoney =
        network.getNews(R.string.api_query_money).subscribeOn(Schedulers.io());

    disposable = Single.merge(newsResultAbuse, newsResultMoney)
        .toList()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(newsResults -> {
          adapterDataModel.clear();
          for (int i = 0; i < newsResults.size(); i++) {
            adapterDataModel.addAll(newsResults.get(i).getItems());
          }
          adapterDataModel.sort();
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          view.hideLoading();
        });
  }

  private OnNewsRecyclerItemClickListener onRecyclerItemClickListener =
      new OnNewsRecyclerItemClickListener<News>() {
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

  @Override public void onDetach() {
    if (disposable != null) {
      disposable.dispose();
    }
  }
}

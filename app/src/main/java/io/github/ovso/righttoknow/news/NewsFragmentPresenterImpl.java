package io.github.ovso.righttoknow.news;

import android.os.Bundle;
import android.util.Log;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.news.model.News;
import io.github.ovso.righttoknow.news.model.NewsResult;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

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

    Single<NewsResult> newsResult1 =
        network.getNews(R.string.api_query_abuse).subscribeOn(Schedulers.io());

    Single<NewsResult> newsResult2 =
        network.getNews(R.string.api_query_money).subscribeOn(Schedulers.io());

    disposable = Single.merge(newsResult1, newsResult2)
        .toList()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<NewsResult>>() {
          @DebugLog @Override public void accept(List<NewsResult> newsResults) throws Exception {
            Log.d("OJH", "sie = " + newsResults.get(0).getItems().size() + newsResults.get(1)
                .getItems()
                .size());
          }
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

package io.github.ovso.righttoknow.news;

import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.news.model.News;
import io.github.ovso.righttoknow.news.model.NewsResult;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * Created by jaeho on 2017. 9. 1
 */

public class NewsFragmentPresenterImpl implements NewsFragmentPresenter {

  private NewsFragmentPresenter.View view;
  private NewsNetwork network = new NewsNetwork(App.getInstance().getApplicationContext(),
      "https://openapi.naver.com");
  private Disposable disposable;
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

  NewsFragmentPresenterImpl(NewsFragmentPresenter.View view) {
    this.view = view;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    adapterDataModel.setOnItemClickListener(onRecyclerItemClickListener);
    req();
  }

  private void req() {
    view.showLoading();
    adapterDataModel.clear();
    view.refresh();
    Single<NewsResult> news1 = network.getNews(R.string.api_query1).subscribeOn(Schedulers.io());
    Single<NewsResult> news2 = network.getNews(R.string.api_query2).subscribeOn(Schedulers.io());
    disposable = Single.merge(news1, news2)
        .subscribeOn(Schedulers.io())
        .toList()
        .map(new Function<List<NewsResult>, List<News>>() {
          @Override public List<News> apply(List<NewsResult> results) throws Exception {
            return NewsResult.sortItems(NewsResult.mergeItems(results));
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<News>>() {
          @Override public void accept(List<News> items) throws Exception {
            adapterDataModel.addAll(items);
            view.refresh();
            view.hideLoading();
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            view.showMessage(R.string.error_server);
            view.hideLoading();
          }
        });
  }

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

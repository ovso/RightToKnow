package io.github.ovso.righttoknow.ui.main.news;

import android.os.Bundle;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.ui.main.news.model.News;

/**
 * Created by jaeho on 2017. 9. 1
 */

public interface NewsFragmentPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterModel(NewsAdapterDataModel dataModel);

  void onRefresh();

  void onDetach();

  interface View {

    void setRecyclerView();

    void setListener();

    void setAdapter();

    void refresh();

    void showLoading();

    void hideLoading();

    void navigateToDetailNews(News item);

    void showSimpleNewsDialog(News item);

    void showMessage(@StringRes int resId);
  }
}

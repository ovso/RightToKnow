package io.github.ovso.righttoknow.news;

import android.os.Bundle;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.news.vo.News;

/**
 * Created by jaeho on 2017. 9. 1
 */

public interface NewsFragmentPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterModel(BaseAdapterDataModel<News> dataModel);

  void onRefresh();

  interface View {

    void setRecyclerView();

    void setListener();

    void setAdapter();

    void refresh();

    void showLoading();

    void hideLoading();
  }
}

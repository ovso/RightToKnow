package io.github.ovso.righttoknow.news;

import android.os.Bundle;

/**
 * Created by jaeho on 2017. 9. 1
 */

public interface NewsFragmentPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterModel(NewsAdapterDataModel dataModel);

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

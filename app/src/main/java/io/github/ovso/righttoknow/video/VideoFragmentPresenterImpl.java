package io.github.ovso.righttoknow.video;

import android.os.Bundle;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoFragmentPresenterImpl implements VideoFragmentPresenter {

  private VideoFragmentPresenter.View view;
  private BaseAdapterDataModel<String> dataModel;
  VideoFragmentPresenterImpl(VideoFragmentPresenter.View view) {
    this.view = view;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setRecyclerView();
    dataModel.add("7kD0ZYzJbYo");
    dataModel.add("7kD0ZYzJbYo");
    dataModel.add("7kD0ZYzJbYo");
    dataModel.add("7kD0ZYzJbYo");

    view.refresh();
  }

  @Override public void setAdapterDataModel(BaseAdapterDataModel<String> dataModel) {
    this.dataModel = dataModel;
  }
}
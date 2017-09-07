package io.github.ovso.righttoknow.video;

import android.os.Bundle;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;

/**
 * Created by jaeho on 2017. 9. 7
 */

public interface VideoFragmentPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterDataModel(BaseAdapterDataModel<String> dataModel);

  interface View {

    void setRecyclerView();

    void refresh();
  }
}
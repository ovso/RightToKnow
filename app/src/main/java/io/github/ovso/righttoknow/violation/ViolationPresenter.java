package io.github.ovso.righttoknow.violation;

import android.os.Bundle;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;

/**
 * Created by jaeho on 2017. 8. 1
 */

public interface ViolationPresenter {

  void onActivityCreated(Bundle savedInstanceState);

  void setAdapterModel(BaseAdapterDataModel adapterDataModel);

  interface View {

    void setRecyclerView();

    void setAdapter();

    void refresh();
  }
}
package io.github.ovso.righttoknow.violator;

import android.content.Context;
import android.os.Bundle;
import io.github.ovso.righttoknow.violator.vo.Violator;

/**
 * Created by jaeho on 2017. 8. 3
 */

public interface ViolatorFragmentPresenter {

  void onActivityCreate(Bundle savedInstanceState);

  void setAdapterModel(ViolatorAdapterDataModel adapterDataModel);

  void onRecyclerItemClick(Violator violator);

  void onDestroyView();

  void onRefresh();

  void onNearbyClick();

  interface View {

    void hideLoading();

    void showLoading();

    void refresh();

    void setAdapter();

    void setRecyclerView();

    void navigateToViolatorDetail(String link);

    void setListener();

    Context getActivity();
  }
}

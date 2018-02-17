package io.github.ovso.righttoknow.violator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.violator.model.Violator;

/**
 * Created by jaeho on 2017. 8. 3
 */

public interface ViolatorFragmentPresenter {

  void onActivityCreate(Bundle savedInstanceState);

  void setAdapterModel(ViolatorAdapterDataModel adapterDataModel);

  void onRecyclerItemClick(Violator violator);

  void onDestroyView();

  void onRefresh();

  void onSearchQuery(String query);

  interface View {

    void hideLoading();

    void showLoading();

    void refresh();

    void setAdapter();

    void setRecyclerView();

    void navigateToViolatorDetail(Violator violator);

    void setListener();

    Context getActivity();

    void setSearchResultText(@StringRes int resId);

    void showMessage(@StringRes int resId);
  }
}

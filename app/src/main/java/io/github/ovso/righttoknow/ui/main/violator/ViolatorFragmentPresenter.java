package io.github.ovso.righttoknow.ui.main.violator;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import io.github.ovso.righttoknow.ui.main.violator.model.Violator;

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

  void onOptionsItemSelected(@IdRes int itemId);

  interface View {

    void hideLoading();

    void showLoading();

    void refresh();

    void setAdapter();

    void setRecyclerView();

    void navigateToViolatorDetail(String link, String address);

    void setListener();

    Context getActivity();

    void setSearchResultText(@StringRes int resId);

    void showMessage(@StringRes int resId);
  }
}

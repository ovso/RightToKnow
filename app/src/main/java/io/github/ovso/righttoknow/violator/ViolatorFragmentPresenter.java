package io.github.ovso.righttoknow.violator;

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

  interface View {

    void hideLoading();

    void showLoading();

    void refresh();

    void setAdapter();

    void setRecyclerView();

    void navigateToViolatorDetail(String link);

    void setListener();

    void showSnackbar(String msg);
  }
}

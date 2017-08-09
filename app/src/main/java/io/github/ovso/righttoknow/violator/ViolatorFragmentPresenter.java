package io.github.ovso.righttoknow.violator;

import android.location.Address;
import android.os.Bundle;
import android.support.annotation.IdRes;
import io.github.ovso.righttoknow.violator.vo.Violator;

/**
 * Created by jaeho on 2017. 8. 3
 */

public interface ViolatorFragmentPresenter {

  void onActivityCreate(Bundle savedInstanceState);

  void setAdapterModel(ViolatorAdapterDataModel adapterDataModel);

  void onRecyclerItemClick(Violator violator);

  void onDestroyView();

  void onMenuSelected(@IdRes int id, Address address);

  interface View {

    void hideLoading();

    void showLoading();

    void refresh();

    void setAdapter();

    void setRecyclerView();

    void navigateToViolatorDetail(String link);

    void setListener();
  }
}

package io.github.ovso.righttoknow.certified;

import android.os.Bundle;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.certified.vo.ChildCertified;

/**
 * Created by jaeho on 2017. 8. 21
 */

public interface CertifiedFragmentPresenter {

  void onActivityCreate(Bundle savedInstanceState);

  void onRecyclerItemClick(ChildCertified certified);


  void setAdapterModel(BaseAdapterDataModel<ChildCertified> adapter);

  void onDestroyView();

  void onRefresh();

  public interface View {

    void setAdapter();

    void setRecyclerView();

    void showLoading();

    void hideLoading();

    void refresh();

    void setListener();

    void navigateToPDFViewer(String name);

  }
}

package io.github.ovso.righttoknow.certified;

import android.os.Bundle;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.certified.vo.ChildCertified;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 21
 */

public class CertifiedFragmentPresenterImpl implements CertifiedFragmentPresenter {

  private CertifiedFragmentPresenter.View view;
  private CertifiedInteractor interactor;
  private BaseAdapterDataModel adapterDataModel;
  CertifiedFragmentPresenterImpl(CertifiedFragmentPresenter.View view) {
    this.view = view;
    interactor = new CertifiedInteractor();
    interactor.setOnChildResultListener(new OnChildResultListener<List<ChildCertified>>() {
      @Override public void onPre() {
        view.showLoading();
      }

      @Override public void onResult(List<ChildCertified> results) {
        adapterDataModel.clear();
        adapterDataModel.addAll(results);
        view.refresh();
      }

      @Override public void onPost() {
        view.hideLoading();
      }

      @Override public void onError() {
        view.hideLoading();
      }
    });
  }

  @Override public void onActivityCreate(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    interactor.req();
  }

  @Override public void onRecyclerItemClick(ChildCertified certified) {
    view.navigateToPDFViewer(certified.getPdf_name());
  }

  @Override public void setAdapterModel(BaseAdapterDataModel<ChildCertified> adapter) {
    adapterDataModel = adapter;
  }

  @Override public void onDestroyView() {
    interactor.cancel();
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    interactor.req();
  }
}

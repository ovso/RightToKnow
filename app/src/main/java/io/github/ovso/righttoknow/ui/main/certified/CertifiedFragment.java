package io.github.ovso.righttoknow.ui.main.certified;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;
import butterknife.BindView;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.network.model.certified.Certified;
import io.github.ovso.righttoknow.data.network.model.certified.VioDataWrapper;
import io.github.ovso.righttoknow.framework.BaseFragment;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.ui.pdfviewer.PDFViewerActivity;
import java.io.File;

public class CertifiedFragment extends BaseFragment implements CertifiedFragmentPresenter.View {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;

  private CertifiedFragmentPresenter presenter;
  private CertifiedAdapter adapter = new CertifiedAdapter();
  private BaseAdapterView adapterView;

  @Override public int getLayoutResId() {
    return R.layout.fragment_certified;
  }

  public static CertifiedFragment newInstance() {
    CertifiedFragment f = new CertifiedFragment();
    return f;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);
    presenter = createPresenter();
    presenter.onActivityCreate(savedInstanceState);
  }

  private CertifiedFragmentPresenter createPresenter() {
    CertifiedFragmentPresenter.View view = this;
    BaseAdapterDataModel<Certified> adapterDataModel = adapter;
    getLifecycle().addObserver(adapter);
    VioDataWrapper wrapper = ((App) getActivity().getApplication()).getVioDataWrapper();
    CertifiedFragmentPresenterImpl p =
        new CertifiedFragmentPresenterImpl(
            view, adapterDataModel, wrapper.vioData
        );
    getLifecycle().addObserver(p);
    return p;
  }

  @Override public void setupRecyclerView() {

    adapterView = adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
    adapter.setOnRecyclerItemClickListener(certified -> presenter.onRecyclerItemClick(certified));
  }

  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;

  @Override public void showLoading() {
    swipeRefresh.setRefreshing(true);
  }

  @Override public void hideLoading() {
    swipeRefresh.setRefreshing(false);
  }

  @Override public void refresh() {
    adapterView.refresh();
  }

  @Override public void setListener() {
    swipeRefresh.setOnRefreshListener(() -> presenter.onRefresh());
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
  }

  @Override public void navigateToPDFViewer(File file) {
    Intent intent = new Intent(getContext(), PDFViewerActivity.class);
    intent.putExtra("file", file);
    startActivity(intent);
  }

  @Override public void showMessage(int resId) {
    Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
  }

  @Override public void onDestroyView() {
    adapter.onDestroyView();
    super.onDestroyView();
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    menu.findItem(R.id.option_menu_search).setVisible(false);
    menu.findItem(R.id.option_menu_sort).setVisible(false);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override public void onResume() {
    super.onResume();
    getActivity().setTitle(R.string.title_certified);
  }
}

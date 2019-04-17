package io.github.ovso.righttoknow.ui.main.certified;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.BaseFragment;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.ui.pdfviewer.PDFViewerActivity;
import java.io.File;

public class CertifiedFragment extends BaseFragment implements CertifiedFragmentPresenter.View {
  private CertifiedFragmentPresenter presenter;

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
    presenter = new CertifiedFragmentPresenterImpl(this);
    presenter.onActivityCreate(savedInstanceState);
  }

  private CertifiedAdapter adapter;
  private BaseAdapterView adapterView;

  @Override public void setAdapter() {
    adapter = new CertifiedAdapter();
    presenter.setAdapterModel(adapter);
    adapterView = adapter;
    adapter.setOnRecyclerItemClickListener(certified -> presenter.onRecyclerItemClick(certified));
  }

  @BindView(R.id.recyclerview) RecyclerView recyclerView;

  @Override public void setRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
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
    presenter.onDestroyView();
    adapter.onDestroyView();
    super.onDestroyView();
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    menu.findItem(R.id.option_menu_sort).setVisible(false);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override public void onResume() {
    super.onResume();
    getActivity().setTitle(R.string.title_certified);
  }
}

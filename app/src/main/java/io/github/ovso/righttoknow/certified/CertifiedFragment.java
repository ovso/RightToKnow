package io.github.ovso.righttoknow.certified;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.certified.vo.ChildCertified;
import io.github.ovso.righttoknow.fragment.BaseFragment;

/**
 * Created by jaeho on 2017. 8. 21
 */

public class CertifiedFragment extends BaseFragment implements CertifiedFragmentPresenter.View {
  private CertifiedFragmentPresenter presenter;

  @Override public int getLayoutResId() {
    return R.layout.fragment_certified;
  }

  public static CertifiedFragment newInstance(Bundle args) {
    CertifiedFragment f = new CertifiedFragment();
    f.setArguments(args);
    return f;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new CertifiedFragmentPresenterImpl(this);
    presenter.onActivityCreate(savedInstanceState);
  }

  private CertifiedAdapter adapter;
  private BaseAdapterView adapterView;

  @Override public void setAdapter() {
    adapter = new CertifiedAdapter();
    presenter.setAdapterModel(adapter);
    adapterView = adapter;
    adapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<ChildCertified>() {
      @Override public void onItemClick(ChildCertified certified) {
        presenter.onRecyclerItemClick(certified);
      }
    });
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
    swipeRefresh.setOnRefreshListener(() -> {
      presenter.onRefresh();
    });
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

  }

  @Override public void navigatePDFViewer(String name) {
    
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    presenter.onDestroyView();
  }
}

package io.github.ovso.righttoknow.violator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.fragment.BaseFragment;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.listener.OnFragmentEventListener;
import io.github.ovso.righttoknow.vfacilitydetail.VFacilityDetailActivity;
import io.github.ovso.righttoknow.violator.model.Violator;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class ViolatorFragment extends BaseFragment
    implements ViolatorFragmentPresenter.View, OnFragmentEventListener {
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  private ViolatorAdapter adapter = new ViolatorAdapter();
  private BaseAdapterView adapterView;
  private ViolatorFragmentPresenter presenter;

  public static ViolatorFragment newInstance() {
    ViolatorFragment f = new ViolatorFragment();
    return f;
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_violator;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new ViolatorFragmentPresenterImpl(this);
    presenter.onActivityCreate(savedInstanceState);
  }

  @Override public void hideLoading() {
    swipeRefresh.setRefreshing(false);
  }

  @Override public void showLoading() {
    swipeRefresh.setRefreshing(true);
  }

  @Override public void refresh() {
    adapterView.refresh();
  }

  @Override public void setAdapter() {
    presenter.setAdapterModel(adapter);
    adapterView = adapter;
    adapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<Violator>() {
      @Override public void onItemClick(Violator violator) {
        presenter.onRecyclerItemClick(violator);
      }
    });
  }

  @Override public void setRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
  }

  @DebugLog @Override public void navigateToViolatorDetail(String link, String address) {

    Intent intent = new Intent(getContext(), VFacilityDetailActivity.class);
    intent.putExtra("violator_link", link);
    intent.putExtra("address", address);
    startActivity(intent);
  }

  @Override public void setListener() {
    swipeRefresh.setOnRefreshListener(() -> {
      presenter.onRefresh();
    });
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
  }

  @BindView(R.id.search_result_textview) TextView searchResultTextView;

  @Override public void setSearchResultText(@StringRes int resId) {
    searchResultTextView.setText(resId);
  }

  @Override public void showMessage(int resId) {
    Snackbar.make(containerView, resId, Snackbar.LENGTH_SHORT).show();
  }

  @BindView(R.id.container_view) View containerView;

  @Override public void onDestroyView() {
    presenter.onDestroyView();
    super.onDestroyView();
  }

  @Override public void onSearchQuery(String query) {
    presenter.onSearchQuery(query);
  }
}
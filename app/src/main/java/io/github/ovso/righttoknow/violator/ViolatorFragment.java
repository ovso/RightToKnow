package io.github.ovso.righttoknow.violator;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.fragment.BaseFragment;
import io.github.ovso.righttoknow.listener.OnFragmentEventListener;
import io.github.ovso.righttoknow.vfacilitydetail.VFacilityDetailActivity;
import io.github.ovso.righttoknow.violator.vo.Violator;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class ViolatorFragment extends BaseFragment
    implements ViolatorFragmentPresenter.View, OnFragmentEventListener<Address> {
  private ViolatorFragmentPresenter presenter;
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;

  public static ViolatorFragment newInstance(Bundle args) {
    ViolatorFragment f = new ViolatorFragment();
    f.setArguments(args);
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

  private ViolatorAdapter violatorAdapter;
  private BaseAdapterView adapterView;
  @BindView(R.id.recyclerview) RecyclerView recyclerView;

  @Override public void setAdapter() {
    violatorAdapter = new ViolatorAdapter();
    presenter.setAdapterModel(violatorAdapter);
    adapterView = violatorAdapter;
    violatorAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<Violator>() {
      @Override public void onItemClick(Violator violator) {
        presenter.onRecyclerItemClick(violator);
      }
    });
  }

  @Override public void setRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(violatorAdapter);
  }

  @DebugLog @Override public void navigateToViolatorDetail(Violator violator) {
    Intent intent = new Intent(getContext(), VFacilityDetailActivity.class);
    intent.putExtra("contents", violator);
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

  @BindView(R.id.container_view) View containerView;

  @Override public void onDestroyView() {
    super.onDestroyView();
    presenter.onDestroyView();
  }

  @Override public void onNearbyClick() {
    presenter.onNearbyClick();
  }

  @Override public void onSearchQuery(String query) {
    presenter.onSearchQuery(query);
  }
}
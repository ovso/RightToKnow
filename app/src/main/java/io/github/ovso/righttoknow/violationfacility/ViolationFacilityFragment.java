package io.github.ovso.righttoknow.violationfacility;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.fragment.BaseFragment;
import io.github.ovso.righttoknow.listener.OnFragmentEventListener;
import io.github.ovso.righttoknow.vfacilitydetail.VFacilityDetailActivity;
import io.github.ovso.righttoknow.violationfacility.model.ViolationFacility;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class ViolationFacilityFragment extends BaseFragment
    implements ViolationFacilityPresenter.View, OnFragmentEventListener<Address> {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
  private ViolationFacilityPresenter presenter;

  public static ViolationFacilityFragment newInstance() {
    ViolationFacilityFragment f = new ViolationFacilityFragment();
    return f;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new ViolationFacilityPresenterImpl(this);
    presenter.onActivityCreated(savedInstanceState);
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_violation;
  }

  @Override public void setRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(violationFacilityAdapter);
  }

  private ViolationFacilityAdapter violationFacilityAdapter;
  private BaseAdapterView adapterView;

  @Override public void setAdapter() {
    violationFacilityAdapter = new ViolationFacilityAdapter();
    presenter.setAdapterModel(violationFacilityAdapter);
    adapterView = violationFacilityAdapter;
    violationFacilityAdapter.setOnRecyclerItemClickListener(
        new OnRecyclerItemClickListener<ViolationFacility>() {
          @Override public void onItemClick(ViolationFacility violationFacility) {
            presenter.onRecyclerItemClick(violationFacility);
          }
        });
  }

  @Override public void refresh() {
    adapterView.refresh();
  }

  @Override public void showLoading() {
    swipeRefresh.setRefreshing(true);
  }

  @Override public void hideLoading() {
    swipeRefresh.setRefreshing(false);
  }

  @Override public void navigateToViolationFacilityDetail(ViolationFacility fac) {
    Intent intent = new Intent(getContext(), VFacilityDetailActivity.class);
    intent.putExtra("contents", fac);
    startActivity(intent);
  }

  @Override public void setListener() {
    swipeRefresh.setOnRefreshListener(() -> presenter.onRefresh());
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
  }

  @BindView(R.id.search_result_textview) TextView searchResultTextView;

  @Override public void setSearchResultText(int resId) {
    searchResultTextView.setText(resId);
  }

  @Override public void showMessage(int resId) {
    Snackbar.make(containerView, resId, Snackbar.LENGTH_SHORT).show();
  }

  @BindView(R.id.container_view) View containerView;

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

  @Override public void onNearbyClick() {
    presenter.onNearbyClick();
  }

  @Override public void onSearchQuery(String query) {
    presenter.onSearchQuery(query);
  }

  @Override public void onDetach() {
    presenter.onDetach();
    super.onDetach();
  }
}
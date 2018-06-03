package io.github.ovso.righttoknow.violationfacility;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.BaseFragment;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.framework.listener.OnFragmentEventListener;
import io.github.ovso.righttoknow.vfacilitydetail.VFacilityDetailActivity;
import io.github.ovso.righttoknow.violationfacility.model.VioFac;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class ViolationFacilityFragment extends BaseFragment
    implements ViolationFacilityPresenter.View, OnFragmentEventListener {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;

  private ViolationFacilityAdapter adapter = new ViolationFacilityAdapter();
  private BaseAdapterView adapterView;
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
    recyclerView.setAdapter(adapter);
  }

  @Override public void setAdapter() {
    presenter.setAdapterModel(adapter);
    adapterView = adapter;
    adapter.setOnRecyclerItemClickListener(
        new OnRecyclerItemClickListener<VioFac>() {
          @Override public void onItemClick(VioFac violationFacility) {
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

  @Override public void navigateToViolationFacilityDetail(String webLink, String address) {

    Intent intent = new Intent(getContext(), VFacilityDetailActivity.class);
    intent.putExtra("vio_fac_link", webLink);
    intent.putExtra("address" , address);
    startActivity(intent);

  }

  @Override public void setListener() {
    swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        presenter.onRefresh();
      }
    });
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    setHasOptionsMenu(true);
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

  @Override public void onSearchQuery(String query) {
    presenter.onSearchQuery(query);
  }

  @Override public void onDetach() {
    presenter.onDetach();
    super.onDetach();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    presenter.onOptionsItemSelected(item.getItemId());
    return super.onOptionsItemSelected(item);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override public void onResume() {
    super.onResume();
    getActivity().setTitle(R.string.title_vioation_facility_inquiry);
  }
}
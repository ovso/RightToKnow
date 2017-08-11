package io.github.ovso.righttoknow.violationfacility;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.wang.avi.AVLoadingIndicatorView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.fragment.BaseFragment;
import io.github.ovso.righttoknow.listener.OnFragmentEventListener;
import io.github.ovso.righttoknow.vfacilitydetail.VFacilityDetailActivity;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class ViolationFacilityFragment extends BaseFragment
    implements ViolationFacilityPresenter.View, OnFragmentEventListener<Address> {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  @BindView(R.id.loading_view) AVLoadingIndicatorView loadingView;
  private ViolationFacilityPresenter presenter;

  public static ViolationFacilityFragment newInstance(Bundle args) {
    ViolationFacilityFragment f = new ViolationFacilityFragment();
    f.setArguments(args);
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
    loadingView.show();
  }

  @Override public void hideLoading() {
    loadingView.hide();
  }

  @Override public void navigateToViolationFacilityDetail(String link) {
    Intent intent = new Intent(getContext(), VFacilityDetailActivity.class);
    intent.putExtra("link", link);
    intent.putExtra("from", R.layout.fragment_violation);
    startActivity(intent);
  }

  @Override public void setListener() {
  }

  @BindView(R.id.container_view) View containerView;

  @Override public void onDestroyView() {
    super.onDestroyView();
    presenter.onDestroyView();
  }

  @Override public void onMenuSelected(@IdRes int id, Address address) {
    presenter.onMenuSelected(id, address);
  }

}
package io.github.ovso.righttoknow.violationfacility;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.fragment.BaseFragment;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class ViolationFacilityFragment extends BaseFragment implements ViolationFacilityPresenter.View {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
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
  }

  @Override public void refresh() {
    adapterView.refresh();
  }

  @Override public void showLoading() {

  }

  @Override public void hideLoading() {

  }
}
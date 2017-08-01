package io.github.ovso.righttoknow.violation;

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

public class ViolationFragment extends BaseFragment implements ViolationPresenter.View {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  private ViolationPresenter presenter;

  public static ViolationFragment newInstance(Bundle args) {
    ViolationFragment f = new ViolationFragment();
    f.setArguments(args);
    return f;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new ViolationPresenterImpl(this);
    presenter.onActivityCreated(savedInstanceState);
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_violation;
  }

  @Override public void setRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(new ViolationAdapter());
  }

  private ViolationAdapter violationAdapter;
  private BaseAdapterView adapterView;

  @Override public void setAdapter() {
    violationAdapter = new ViolationAdapter();
    presenter.setAdapterModel(violationAdapter);
    adapterView = violationAdapter;
  }

  @Override public void refresh() {
    adapterView.refresh();
  }
}
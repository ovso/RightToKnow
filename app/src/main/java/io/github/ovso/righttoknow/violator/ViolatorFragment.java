package io.github.ovso.righttoknow.violator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.fragment.BaseFragment;
import io.github.ovso.righttoknow.vfacilitydetail.VFacilityDetailActivity;
import io.github.ovso.righttoknow.violator.vo.Violator;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class ViolatorFragment extends BaseFragment implements ViolatorFragmentPresenter.View {
  private ViolatorFragmentPresenter presenter;

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

  }

  @Override public void showLoading() {

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

  @DebugLog @Override public void navigateToViolatorDetail(String link) {
    Intent intent = new Intent(getContext(), VFacilityDetailActivity.class);
    intent.putExtra("link", link);
    startActivity(intent);

  }
}
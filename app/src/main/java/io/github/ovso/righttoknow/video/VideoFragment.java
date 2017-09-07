package io.github.ovso.righttoknow.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.fragment.BaseFragment;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoFragment extends BaseFragment implements VideoFragmentPresenter.View {

  private VideoFragmentPresenter presenter;

  public static VideoFragment newInstance(Bundle args) {
    VideoFragment f = new VideoFragment();
    f.setArguments(args);
    return f;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new VideoFragmentPresenterImpl(this);
    presenter.onActivityCreated(savedInstanceState);
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_video;
  }

  private BaseAdapterView adapterView;

  @BindView(R.id.recyclerview) RecyclerView recyclerView;

  @Override public void setRecyclerView() {
    LinearLayoutManager layout = new LinearLayoutManager(getContext());
    VideoAdapter adapter = new VideoAdapter();
    presenter.setAdapterDataModel(adapter);
    adapterView = adapter;
    recyclerView.setLayoutManager(layout);
    recyclerView.setAdapter(adapter);

  }

  @Override public void refresh() {
    adapterView.refresh();
  }
}

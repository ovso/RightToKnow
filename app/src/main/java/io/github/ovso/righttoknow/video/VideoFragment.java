package io.github.ovso.righttoknow.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import butterknife.BindView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.fragment.BaseFragment;
import io.github.ovso.righttoknow.video.vo.Video;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoFragment extends BaseFragment implements VideoFragmentPresenter.View {
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
  private VideoFragmentPresenter presenter;

  public static VideoFragment newInstance(Bundle args) {
    VideoFragment f = new VideoFragment();
    f.setArguments(args);
    return f;
  }

  @DebugLog @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.main_video_portrait, menu);
    super.onCreateOptionsMenu(menu, inflater);
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

  @DebugLog @Override public void navigateToVideoDetail(Video video) {
    int startTimeMillis = 0;
    boolean autoPlay = true;
    boolean lightboxMode = false;
    Intent intent =
        YouTubeStandalonePlayer.createVideoIntent(getActivity(), Constants.DEVELOPER_KEY,
            video.getUrl().split("v=")[1], startTimeMillis, autoPlay, lightboxMode);
    startActivity(intent);
  }

  @Override public void setRefreshLayout() {
    swipeRefresh.setOnRefreshListener(() -> {
      presenter.onRefresh();
    });
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
  }

  @Override public void showLoading() {
    swipeRefresh.setRefreshing(true);
  }

  @Override public void hideLoading() {
    swipeRefresh.setRefreshing(false);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    presenter.onDestroyView();
  }
}

package io.github.ovso.righttoknow.ui.main.video;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.data.ActivityReqCode;
import io.github.ovso.righttoknow.data.network.model.video.SearchItem;
import io.github.ovso.righttoknow.framework.DaggerFragment;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.ui.base.OnEndlessRecyclerScrollListener;
import io.github.ovso.righttoknow.ui.base.OnRecyclerViewItemClickListener;
import io.github.ovso.righttoknow.ui.base.VideoRecyclerView;
import io.github.ovso.righttoknow.ui.video.LandscapeVideoActivity;
import io.github.ovso.righttoknow.ui.video.PortraitVideoActivity;
import io.github.ovso.righttoknow.ui.videodetail.VideoDetailActivity;
import javax.inject.Inject;

public class VideoFragment extends DaggerFragment implements VideoFragmentPresenter.View,
    OnEndlessRecyclerScrollListener.OnLoadMoreListener,
    OnRecyclerViewItemClickListener<SearchItem> {
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
  @BindView(R.id.recyclerview) VideoRecyclerView recyclerView;
  @Inject VideoFragmentPresenter presenter;
  private Menu menu;
  private MenuInflater menuInflater;
  private BaseAdapterView adapterView;

  public static VideoFragment newInstance() {
    VideoFragment f = new VideoFragment();
    return f;
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    this.menu = menu;
    this.menuInflater = inflater;
    presenter.onCreateOptionsMenu();
    menu.findItem(R.id.option_menu_search).setVisible(false);
    menu.findItem(R.id.option_menu_sort).setVisible(false);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    return presenter.onOptionsItemSelected(item.getItemId());
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter.onActivityCreated(savedInstanceState);
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_video;
  }

  @Override public void setRecyclerView() {
    LinearLayoutManager layout = new LinearLayoutManager(getContext());
    VideoAdapter adapter = new VideoAdapter();
    presenter.setAdapterDataModel(adapter);
    adapterView = adapter;
    recyclerView.setLayoutManager(layout);
    recyclerView.setAdapter(adapter);
    recyclerView.addOnScrollListener(
        new OnEndlessRecyclerScrollListener
            .Builder((LinearLayoutManager) recyclerView.getLayoutManager(), this)
            .setVisibleThreshold(20)
            .build()
    );
    recyclerView.setOnItemClickListener(this);
  }

  @Override public void refresh() {
    adapterView.refresh();
  }

  private void navigateToPortraitVideo(String videoId, int startTimeMillis, boolean autoPlay,
      boolean lightboxMode) {
    Intent intent =
        YouTubeStandalonePlayer.createVideoIntent(getActivity(),
            Security.GOOGLE_API_KEY.getValue(),
            videoId, startTimeMillis, autoPlay, lightboxMode);
    startActivityForResult(intent, ActivityReqCode.YOUTUBE.get());
  }

  private void navigateToLandscapeVideo(String videoId) {
    Intent intent = new Intent(getContext(), VideoDetailActivity.class);
    intent.putExtra("video_id", videoId);
    startActivityForResult(intent, ActivityReqCode.YOUTUBE.get());
  }

  @Override public void setRefreshLayout() {
    swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        presenter.onRefresh();
      }
    });
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
  }

  @Override public void showLoading() {
    swipeRefresh.setRefreshing(true);
  }

  @Override public void hideLoading() {
    swipeRefresh.setRefreshing(false);
  }

  @Override public void setLandscapeMode() {
    menuInflater.inflate(R.menu.main_video_landscape, menu);
  }

  @Override public void setPortraitMode() {
    menuInflater.inflate(R.menu.main_video_portrait, menu);
  }

  @Override public void clearMenuMode() {
    menu.clear();
  }

  @Override public void showMessage(int resId) {
    Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
  }

  @Override public void setLoaded() {
    recyclerView.getOnEndlessRecyclerScrollListener().setLoaded();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    presenter.onDestroyView();
  }

  @Override public void onResume() {
    super.onResume();
    getActivity().setTitle(R.string.title_video);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    presenter.onActivityResult(requestCode, resultCode, data);
  }

  @Override public void onLoadMore() {
    presenter.onLoadMore();
  }

  @Override public void onItemClick(View view, SearchItem data, int itemPosition) {
    presenter.onItemClick(data);
  }

  @Override public void showVideoTypeDialog(DialogInterface.OnClickListener onClickListener) {
    new android.app.AlertDialog.Builder(getContext()).setMessage(
        R.string.please_select_the_player_mode)
        .setPositiveButton(R.string.portrait_mode,
            onClickListener)
        .setNeutralButton(R.string.landscape_mode, onClickListener)
        .setNegativeButton(android.R.string.cancel, onClickListener)
        .show();
  }

  @Override public void showPortraitVideo(String videoId) {
    Intent intent = new Intent(getContext(), PortraitVideoActivity.class);
    intent.putExtra("video_id", videoId);
    startActivity(intent);
  }

  @Override public void showLandscapeVideo(String videoId) {
    Intent intent = new Intent(getContext(), LandscapeVideoActivity.class);
    intent.putExtra("video_id", videoId);
    startActivity(intent);
  }

  @Override public void showYoutubeUseWarningDialog() {
    new android.app.AlertDialog.Builder(getActivity()).setMessage(R.string.youtube_use_warning)
        .setPositiveButton(android.R.string.ok, null)
        .show();
  }
}
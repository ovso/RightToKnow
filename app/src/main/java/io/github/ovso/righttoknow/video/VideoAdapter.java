package io.github.ovso.righttoknow.video;

import android.support.annotation.NonNull;
import android.view.View;
import butterknife.BindView;
import com.codewaves.youtubethumbnailview.ThumbnailLoadingListener;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.video.vo.Video;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoAdapter extends BaseRecyclerAdapter
    implements VideoAdapterDataModel, BaseAdapterView {
  List<Video> toBeUsedItems = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new VideoViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_video_item;
  }

  private ThumbnailLoadingListener onThumbnailLoadingListener = new ThumbnailLoadingListener() {
    @Override public void onLoadingStarted(@NonNull String url, @NonNull View view) {
      showLoading(view);
    }

    @Override public void onLoadingComplete(@NonNull String youtubeUrl, @NonNull View view) {
      ThumbnailView thumbnailView = ((ThumbnailView) view);
      thumbnailView.loadThumbnail(youtubeUrl,
          url -> Picasso.with(view.getContext()).load(url).get());
      hideLoading(view);
    }

    @Override public void onLoadingCanceled(@NonNull String url, @NonNull View view) {
      hideLoading(view);
    }

    @Override
    public void onLoadingFailed(@NonNull String url, @NonNull View view, Throwable error) {
      View rootView = (View) view.getParent();
      if (rootView != null) {
        hideLoading(rootView);
      }
    }
  };

  private void showLoading(View view) {
    View rootView = (View) view.getParent();
    if (rootView != null) {
      AVLoadingIndicatorView progressBar = rootView.findViewById(R.id.progress_bar);
      progressBar.show();
    }
  }

  private void hideLoading(View view) {
    View rootView = (View) view.getParent();
    if (rootView != null) {
      AVLoadingIndicatorView progressBar = rootView.findViewById(R.id.progress_bar);
      progressBar.hide();
    }
  }

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
    if (holder instanceof VideoViewHolder) {
      VideoViewHolder viewHolder = (VideoViewHolder) holder;
      Video video = toBeUsedItems.get(position);
      viewHolder.thumbnailView.loadThumbnail(video.getUrl(), onThumbnailLoadingListener);

      viewHolder.thumbnailView.setOnClickListener(view -> {
        if (onRecyclerItemClickListener != null) {
          onRecyclerItemClickListener.onItemClick(video);
        }
      });
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  @Override public void add(Video item) {
    toBeUsedItems.add(item);
  }

  @Override public void addAll(List<Video> items) {
    toBeUsedItems.addAll(items);
  }

  @Override public Video remove(int position) {
    return toBeUsedItems.remove(position);
  }

  @Override public Video getItem(int position) {
    return toBeUsedItems.get(position);
  }

  @Override public void add(int index, Video item) {
    toBeUsedItems.add(index, item);
  }

  @Override public int getSize() {
    return toBeUsedItems.size();
  }

  @Override public void clear() {
    toBeUsedItems.clear();
  }

  private OnRecyclerItemClickListener<Video> onRecyclerItemClickListener;

  @Override public void setOnItemClickListener(OnRecyclerItemClickListener<Video> listener) {
    onRecyclerItemClickListener = listener;
  }

  static class VideoViewHolder extends BaseViewHolder {
    @BindView(R.id.thumbnail) ThumbnailView thumbnailView;
    @BindView(R.id.progress_bar) AVLoadingIndicatorView progressBar;

    public VideoViewHolder(View itemView) {
      super(itemView);
    }
  }
}

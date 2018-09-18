package io.github.ovso.righttoknow.ui.main.video;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.wang.avi.AVLoadingIndicatorView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.ui.main.video.model.Video;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoAdapter extends BaseRecyclerAdapter
    implements VideoAdapterDataModel, BaseAdapterView {
  private List<Video> items = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new VideoViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_video_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
    if (holder instanceof VideoViewHolder) {
      final VideoViewHolder viewHolder = (VideoViewHolder) holder;
      final Video video = items.get(position);
      viewHolder.removeThumbnailView();
      viewHolder.addThumbnailView();
      YouTubeThumbnailView thumbnailView = viewHolder.thumbnailView;
      final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener =
          new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView thumbnail, String video_id) {
              viewHolder.hideLoading();
            }

            @Override public void onThumbnailError(YouTubeThumbnailView thumbnail,
                YouTubeThumbnailLoader.ErrorReason errorReason) {
              viewHolder.hideLoading();
            }
          };
      YouTubeThumbnailView.OnInitializedListener onInitializedListener =
          new YouTubeThumbnailView.OnInitializedListener() {
            @Override public void onInitializationSuccess(YouTubeThumbnailView thumbnail,
                YouTubeThumbnailLoader loader) {
              loader.setVideo(video.getVideo_id());
              loader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override public void onInitializationFailure(YouTubeThumbnailView thumbnail,
                YouTubeInitializationResult result) {
              viewHolder.hideLoading();
            }
          };

      thumbnailView.initialize(Security.GOOGLE_API_KEY.getValue(), onInitializedListener);

      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (onRecyclerItemClickListener != null) {
            onRecyclerItemClickListener.onItemClick(video);
          }
        }
      });

      viewHolder.titleTextView.setText(video.getTitle());
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  @Override public void add(Video item) {
    items.add(item);
  }

  @Override public void addAll(List<Video> items) {
    this.items.addAll(items);
  }

  @Override public Video remove(int position) {
    return items.remove(position);
  }

  @Override public Video getItem(int position) {
    return items.get(position);
  }

  @Override public void add(int index, Video item) {
    items.add(index, item);
  }

  @Override public int getSize() {
    return items.size();
  }

  @Override public void clear() {
    items.clear();
  }

  private OnRecyclerItemClickListener<Video> onRecyclerItemClickListener;

  @Override public void setOnItemClickListener(OnRecyclerItemClickListener<Video> listener) {
    onRecyclerItemClickListener = listener;
  }

  static class VideoViewHolder extends BaseViewHolder {
    YouTubeThumbnailView thumbnailView;
    @BindView(R.id.progress_bar) AVLoadingIndicatorView progressBar;
    @BindView(R.id.thumbnail_container) FrameLayout thumbnailContainer;
    @BindView(R.id.thumbnail_title_textview) TextView titleTextView;

    VideoViewHolder(View itemView) {
      super(itemView);
      showLoading();
    }

    private void addThumbnailView() {
      thumbnailView = new YouTubeThumbnailView(itemView.getContext().getApplicationContext());
      ViewGroup.LayoutParams params =
          new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.MATCH_PARENT);
      thumbnailView.setLayoutParams(params);
      thumbnailView.setScaleType(ImageView.ScaleType.FIT_XY);
      thumbnailContainer.addView(thumbnailView, 0);
    }

    private void removeThumbnailView() {
      if (thumbnailView != null) {
        thumbnailContainer.removeView(thumbnailView);
      }
    }

    public void hideLoading() {
      progressBar.smoothToHide();
    }

    public void showLoading() {
      progressBar.smoothToShow();
    }
  }
}

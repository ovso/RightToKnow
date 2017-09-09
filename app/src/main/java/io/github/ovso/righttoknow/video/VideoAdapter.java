package io.github.ovso.righttoknow.video;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.wang.avi.AVLoadingIndicatorView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.video.vo.Video;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
    if (holder instanceof VideoViewHolder) {
      VideoViewHolder viewHolder = (VideoViewHolder) holder;
      Video video = toBeUsedItems.get(position);
      viewHolder.removeThumbnailView();
      viewHolder.addThumbnailView();
      YouTubeThumbnailView thumbnailView = viewHolder.thumbnailView;
      YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener =
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

      thumbnailView.initialize(Constants.DEVELOPER_KEY, onInitializedListener);

      thumbnailView.setOnClickListener(view -> {
        if (onRecyclerItemClickListener != null) {
          onRecyclerItemClickListener.onItemClick(video);
        }
      });

      viewHolder.titleTextView.setText("[" + video.getDate() + "]" + video.getTitle());
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

  @Override public void sort() {
    Collections.sort(toBeUsedItems, (o1, o2) -> {
      try {
        String o1String = o1.getDate();
        String o2String = o2.getDate();

        Date o1Date = new SimpleDateFormat("yyyy-MM-dd").parse(o1String);
        Date o2Date = new SimpleDateFormat("yyyy-MM-dd").parse(o2String);
        return o2Date.compareTo(o1Date);
      } catch (ParseException e) {
        e.printStackTrace();
        return 0;
      }
    });
  }

  static class VideoViewHolder extends BaseViewHolder {
    YouTubeThumbnailView thumbnailView;
    @BindView(R.id.progress_bar) AVLoadingIndicatorView progressBar;
    @BindView(R.id.thumbnail_container) FrameLayout thumbnailContainer;
    @BindView(R.id.thumbnail_title_textview) TextView titleTextView;

    public VideoViewHolder(View itemView) {
      super(itemView);
      showLoading();
    }

    private void addThumbnailView() {
      thumbnailView = new YouTubeThumbnailView(itemView.getContext().getApplicationContext());
      ViewGroup.LayoutParams params =
          new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.MATCH_PARENT);
      thumbnailView.setLayoutParams(params);
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

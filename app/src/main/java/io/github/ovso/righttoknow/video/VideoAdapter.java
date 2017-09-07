package io.github.ovso.righttoknow.video;

import android.view.View;
import butterknife.BindView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.BaseRecyclerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoAdapter extends BaseRecyclerAdapter
    implements BaseAdapterDataModel<String>, BaseAdapterView {
  List<String> toBeUsedItems = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new VideoViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_video_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
    if (holder instanceof VideoViewHolder) {

      final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener =
          new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView,
                YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
              //youTubeThumbnailView.setVisibility(View.VISIBLE);
              //holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
          };

      YouTubeThumbnailView thumbnailView = ((VideoViewHolder) holder).thumbnailView;
      thumbnailView.initialize("AIzaSyBdY9vP4_vQs5YEGJ3Ghu6s5gGY8yFlo0s",
          new YouTubeThumbnailView.OnInitializedListener() {
            @Override public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView,
                YouTubeThumbnailLoader youTubeThumbnailLoader) {
              youTubeThumbnailLoader.setVideo("PuaYhnGmeEk");
              youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView,
                YouTubeInitializationResult youTubeInitializationResult) {
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

  @Override public void add(String item) {
    toBeUsedItems.add(item);
  }

  @Override public void addAll(List<String> items) {
    toBeUsedItems.addAll(items);
  }

  @Override public String remove(int position) {
    return toBeUsedItems.remove(position);
  }

  @Override public String getItem(int position) {
    return toBeUsedItems.get(position);
  }

  @Override public void add(int index, String item) {
    toBeUsedItems.add(index, item);
  }

  @Override public int getSize() {
    return toBeUsedItems.size();
  }

  @Override public void clear() {
    toBeUsedItems.clear();
  }

  static class VideoViewHolder extends BaseViewHolder {
    @BindView(R.id.youtube_thumbnail_view) YouTubeThumbnailView thumbnailView;

    public VideoViewHolder(View itemView) {
      super(itemView);
    }
  }
}

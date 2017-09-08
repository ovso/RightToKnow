package io.github.ovso.righttoknow.video;

import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.squareup.picasso.Picasso;
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

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
    if (holder instanceof VideoViewHolder) {
      VideoViewHolder viewHolder = (VideoViewHolder) holder;
      ((VideoViewHolder) holder).thumbnailView.loadThumbnail(
          toBeUsedItems.get(position).getUrl(),
          url -> Picasso.with(viewHolder.thumbnailView.getContext()).load(url).get());

      Video video = toBeUsedItems.get(position);
      viewHolder.itemView.setOnClickListener(view -> {
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
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    public VideoViewHolder(View itemView) {
      super(itemView);
    }
  }
}

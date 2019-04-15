package io.github.ovso.righttoknow.ui.main.video;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import io.github.ovso.righttoknow.data.network.model.video.SearchItem;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.ui.base.OnRecyclerViewItemClickListener;
import io.github.ovso.righttoknow.ui.main.video.adapter.Bindable;
import io.github.ovso.righttoknow.ui.main.video.adapter.VideoViewHolder;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder>
    implements BaseAdapterDataModel<SearchItem>, BaseAdapterView {
  private List<SearchItem> items = new ArrayList<>();
  @Setter private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

  @NonNull @Override
  public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return VideoViewHolder.create(parent);
  }

  @Override public void onBindViewHolder(VideoViewHolder holder, int position) {
    if (holder instanceof Bindable) {
      ((Bindable) holder).bind(getItem(position));
      holder.setOnRecyclerViewItemClickListener(onRecyclerViewItemClickListener);
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  @Override public void add(SearchItem item) {
    items.add(item);
  }

  @Override public void addAll(List<SearchItem> items) {
    this.items.addAll(items);
  }

  @Override public SearchItem remove(int position) {
    return items.remove(position);
  }

  @Override public SearchItem getItem(int position) {
    return items.get(position);
  }

  @Override public void add(int index, SearchItem item) {
    items.add(index, item);
  }

  @Override public int getSize() {
    return items.size();
  }

  @Override public void clear() {
    items.clear();
  }
}

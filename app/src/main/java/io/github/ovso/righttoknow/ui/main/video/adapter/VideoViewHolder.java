package io.github.ovso.righttoknow.ui.main.video.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.network.model.video.SearchItem;
import io.github.ovso.righttoknow.ui.base.OnRecyclerViewItemClickListener;
import io.github.ovso.righttoknow.utils.DateUtils;
import java.util.Date;
import lombok.Setter;

public class VideoViewHolder extends RecyclerView.ViewHolder implements Bindable<SearchItem> {
  private SearchItem data;
  @BindView(R.id.thumbnail_image_view) AppCompatImageView thumbnailImageView;
  @BindView(R.id.title_text_view) TextView titleTextView;
  @BindView(R.id.date_text_view) TextView dateTextView;
  @Setter private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

  private VideoViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public static VideoViewHolder create(ViewGroup parent) {
    return new VideoViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_video, parent, false));
  }

  @Override public void bind(SearchItem $data) {
    data = $data;
    titleTextView.setText($data.getSnippet().getTitle());
    Date date = $data.getSnippet().getPublishedAt();
    dateTextView.setText(DateUtils.getDate(date, "yyyy년 MM월 dd일 HH시 mm분"));
    $data.getSnippet().getPublishedAt();
    Glide.with(itemView.getContext())
        .load($data.getSnippet().getThumbnails().getMedium().getUrl())
        .into(thumbnailImageView);
  }

  @OnClick(R.id.play_button) void onClick(View view) {
    onRecyclerViewItemClickListener.onItemClick(view, data, 0);
  }
}

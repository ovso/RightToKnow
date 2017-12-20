package io.github.ovso.righttoknow.news;

import android.text.Html;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.common.Utility;
import io.github.ovso.righttoknow.news.model.News;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 9. 1
 */

public class NewsAdapter extends BaseRecyclerAdapter
    implements NewsAdapterDataModel, BaseAdapterView {
  private List<News> toBeUsedItems = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new NewsViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_news_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
    if (viewHolder instanceof NewsViewHolder) {
      NewsViewHolder holder = (NewsViewHolder) viewHolder;
      News news = toBeUsedItems.get(position);
      String title = news.getTitle();
      //title = Utility.getActionEmoji(new String[] { title }) + title;
      SpannableString spannableString = new SpannableString(Html.fromHtml(title));
      holder.titleTextview.setText(spannableString);

      String date = Utility.convertDate(news.getPubDate(), "yyyy-MM-dd");
      holder.dateTextView.setText(date);
      holder.itemView.setOnClickListener(view -> {
        if (onRecyclerItemClickListener != null) {
          onRecyclerItemClickListener.onItemClick(news);
        }
      });
      holder.imageView.setOnClickListener(
          view -> onRecyclerItemClickListener.onSimpleNewsItemClick(news));
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void add(News item) {
    toBeUsedItems.add(item);
  }

  @Override public void addAll(List<News> items) {
    toBeUsedItems.addAll(items);
  }

  @Override public News remove(int position) {
    return toBeUsedItems.remove(position);
  }

  @Override public News getItem(int position) {
    return toBeUsedItems.get(position);
  }

  @Override public void add(int index, News item) {
    toBeUsedItems.add(index, item);
  }

  @Override public int getSize() {
    return toBeUsedItems.size();
  }

  @Override public void clear() {
    toBeUsedItems.clear();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  private OnNewsRecyclerItemClickListener<News> onRecyclerItemClickListener;

  @Override public void setOnItemClickListener(OnNewsRecyclerItemClickListener<News> listener) {
    onRecyclerItemClickListener = listener;
  }

  final static class NewsViewHolder extends BaseViewHolder {
    @BindView(R.id.title_textview) TextView titleTextview;
    @BindView(R.id.date_textview) TextView dateTextView;
    @BindView(R.id.simple_news_imageview) ImageView imageView;

    public NewsViewHolder(View itemView) {
      super(itemView);
    }
  }
}

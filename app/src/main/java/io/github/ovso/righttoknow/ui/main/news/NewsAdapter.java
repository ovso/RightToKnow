package io.github.ovso.righttoknow.ui.main.news;

import android.text.Html;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.utils.Utility;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.ui.main.news.model.News;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 9. 1
 */

public class NewsAdapter extends BaseRecyclerAdapter
    implements NewsAdapterDataModel, BaseAdapterView {
  private List<News> items = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new NewsViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_news_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
    if (viewHolder instanceof NewsViewHolder) {
      NewsViewHolder holder = (NewsViewHolder) viewHolder;
      final News news = items.get(position);
      String title = news.getTitle();
      SpannableString spannableString = new SpannableString(Html.fromHtml(title));
      holder.titleTextview.setText(spannableString);
      holder.countTextview.setText(String.valueOf(position+1));
      String date = Utility.convertDate(news.getPubDate(), "yy-MM-dd");
      holder.dateTextView.setText(date);
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (onRecyclerItemClickListener != null) {
            onRecyclerItemClickListener.onItemClick(news);
          }
        }
      });
      holder.imageView.setOnClickListener(
          new View.OnClickListener() {
            @Override public void onClick(View view) {
              onRecyclerItemClickListener.onSimpleNewsItemClick(news);
            }
          });
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void add(News item) {
    items.add(item);
  }

  @Override public void addAll(List<News> items) {
    this.items.addAll(items);
  }

  @Override public News remove(int position) {
    return items.remove(position);
  }

  @Override public News getItem(int position) {
    return items.get(position);
  }

  @Override public void add(int index, News item) {
    items.add(index, item);
  }

  @Override public int getSize() {
    return items.size();
  }

  @Override public void clear() {
    items.clear();
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
    @BindView(R.id.count_textview) TextView countTextview;
    @BindView(R.id.date_textview) TextView dateTextView;
    @BindView(R.id.simple_news_imageview) ImageView imageView;

    public NewsViewHolder(View itemView) {
      super(itemView);
    }
  }
}

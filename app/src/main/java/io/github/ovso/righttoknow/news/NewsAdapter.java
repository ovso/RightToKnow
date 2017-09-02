package io.github.ovso.righttoknow.news;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.news.vo.News;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 9. 1
 */

public class NewsAdapter extends BaseRecyclerAdapter
    implements BaseAdapterDataModel<News>, BaseAdapterView {
  private List<News> toBeUsedItems = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new NewsViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_news_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder holder, int position) {
    if(holder instanceof NewsViewHolder) {
      ((NewsViewHolder) holder).titleTextview.setText(toBeUsedItems.get(position).getTitle());
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

  final static class NewsViewHolder extends BaseViewHolder {
    @BindView(R.id.title_textview) TextView titleTextview;

    public NewsViewHolder(View itemView) {
      super(itemView);
    }
  }
}

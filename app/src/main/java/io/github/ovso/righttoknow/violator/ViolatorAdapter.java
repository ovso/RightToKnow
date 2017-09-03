package io.github.ovso.righttoknow.violator;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.common.ObjectUtils;
import io.github.ovso.righttoknow.common.Utility;
import io.github.ovso.righttoknow.violator.vo.Violator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 3
 */

public class ViolatorAdapter extends BaseRecyclerAdapter
    implements BaseAdapterView, ViolatorAdapterDataModel<Violator> {

  private List<Violator> toBeUsedItems = new ArrayList<>();
  private List<Violator> originItems = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    if (viewType == ITEM_VIEW_TYPE_HEADER) {
      return new ViolatorHeaderViewHolder(view);
    } else {
      return new ViolatorViewHolder(view);
    }
  }

  @Override public int getLayoutRes(int viewType) {
    if (viewType == ITEM_VIEW_TYPE_HEADER) {
      return R.layout.fragment_violator_item_header;
    } else {
      return R.layout.fragment_violator_item;
    }
  }

  @Override public void onBindViewHolder(BaseViewHolder baseHolder, int position) {
    if (baseHolder instanceof ViolatorViewHolder) {
      ViolatorViewHolder holder = (ViolatorViewHolder) baseHolder;
      Violator violator = toBeUsedItems.get(position);
      holder.turnTextview.setText(String.valueOf(violator.getReg_number()));
      holder.sidoTextView.setText(violator.getSido());
      holder.sigunguTextView.setText(violator.getSigungu());
      holder.violatorTextView.setText(
          violator.getName() + Utility.getActionEmoji(violator.getAction()));
      holder.centerNameTextView.setText(violator.getOld_fac_name());
      String history = violator.getHistory() + holder.violatorTextView.getContext()
          .getString(R.string.violation_history_count);
      holder.historyTextView.setText(history);

      holder.violator = violator;

      holder.onRecyclerItemClickListener = onRecyclerItemClickListener;
    } else if (baseHolder instanceof ViolatorHeaderViewHolder) {
      ViolatorHeaderViewHolder holder = (ViolatorHeaderViewHolder) baseHolder;
      holder.selfAdapter = this;
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  @Override public void add(Violator item) {
    toBeUsedItems.add(item);
  }

  @Override public void addAll(List<Violator> items) {

    originItems.add(new Violator());
    originItems.addAll(items);

    toBeUsedItems.addAll(items);
  }

  @Override public Violator remove(int position) {
    return toBeUsedItems.remove(position);
  }

  @Override public Violator getItem(int position) {
    return toBeUsedItems.get(position);
  }

  @Override public void add(int index, Violator item) {
    toBeUsedItems.add(index, item);
  }

  @Override public int getSize() {
    return toBeUsedItems.size();
  }

  @Setter private OnRecyclerItemClickListener onRecyclerItemClickListener;

  @Override public int getItemViewType(int position) {
    int regNumber = toBeUsedItems.get(position).getReg_number();
    if (regNumber > 0) {
      return ITEM_VIEW_TYPE_DEFAULT;
    } else {
      return ITEM_VIEW_TYPE_HEADER;
    }
  }

  private void sortTurn() {
    Comparator<Violator> comparator = (t1, t2) -> Integer.valueOf(t2.getReg_number())
        .compareTo(Integer.valueOf(t1.getReg_number()));
    Collections.sort(toBeUsedItems, comparator);
  }

  private void sortSido() {
    Comparator<Violator> comparator = (t1, t2) -> t1.getSido().compareTo(t2.getSido());
    Collections.sort(toBeUsedItems, comparator);
  }

  private void sortHistory() {
    Comparator<Violator> comparator =
        (t1, t2) -> Integer.valueOf(t2.getHistory()).compareTo(t1.getHistory());
    Collections.sort(toBeUsedItems, comparator);
  }

  final static class ViolatorViewHolder extends BaseRecyclerAdapter.BaseViewHolder {
    @BindView(R.id.turn_textview) TextView turnTextview;
    @BindView(R.id.sido_textview) TextView sidoTextView;
    @BindView(R.id.sigungu_textview) TextView sigunguTextView;
    @BindView(R.id.violator_textview) TextView violatorTextView;
    @BindView(R.id.name_textview) TextView centerNameTextView;
    @BindView(R.id.history_textview) TextView historyTextView;

    OnRecyclerItemClickListener onRecyclerItemClickListener;
    Violator violator;

    public ViolatorViewHolder(View itemView) {
      super(itemView);
    }

    @OnClick(R.id.container_view) void onItemClick() {
      onRecyclerItemClickListener.onItemClick(violator);
    }
  }

  @Override public void clear() {
    originItems.clear();
    toBeUsedItems.clear();
  }

  @Override public void searchMyLocation(String locality, String subLocality) {
    String nowLocality;
    if (!TextUtils.isEmpty(locality) && !TextUtils.isEmpty(subLocality)) {
      nowLocality = subLocality;
    } else if (!TextUtils.isEmpty(locality) && TextUtils.isEmpty(subLocality)) {
      nowLocality = locality;
    } else {
      nowLocality = subLocality;
    }

    List<Violator> temps = new ArrayList<>();
    for (Violator v : toBeUsedItems) {
      String sigungu = v.getSigungu();
      if (!TextUtils.isEmpty(sigungu)) {
        if (sigungu.indexOf(nowLocality) != -1) {
          temps.add(v);
        }
      }
    }
    toBeUsedItems.clear();
    toBeUsedItems.add(0, new Violator());
    toBeUsedItems.addAll(temps);
  }

  @Override public void searchAllWords(String query) {
    List<Violator> returnItems = new ArrayList<>();

    toBeUsedItems.clear();
    toBeUsedItems.addAll(originItems);

    if (toBeUsedItems.size() > 0) {
      toBeUsedItems.remove(0);
    }
    for (int i = 0; i < toBeUsedItems.size(); i++) {
      Violator item = toBeUsedItems.get(i);
      String trimQuery = query.trim();
      if (item.getSido().contains(trimQuery)
          || item.getSigungu().contains(trimQuery)
          || item.getName().contains(trimQuery)
          || item.getOld_fac_name().contains(trimQuery)
          || item.getAddress().contains(trimQuery)) {
        returnItems.add(item);
        continue;
      }
      if (isSearchQuery(item.getAction(), query)) {
        returnItems.add(item);
        continue;
      }
      if (isSearchQuery(item.getDisposal(), query)) {
        returnItems.add(item);
        continue;
      }
    }
    toBeUsedItems.clear();
    toBeUsedItems.add(new Violator());
    toBeUsedItems.addAll(returnItems);
  }

  boolean isSearchQuery(List<String> strings, String query) {
    if (!ObjectUtils.isEmpty(strings)) {
      for (String a : strings) {
        if (a.contains(query)) return true;
      }
      return false;
    } else {
      return false;
    }
  }

  final static class ViolatorHeaderViewHolder extends BaseRecyclerAdapter.BaseViewHolder {
    @BindView(R.id.turn_textview) TextView turnTextview;
    @BindView(R.id.sido_textview) TextView sidoTextView;
    @BindView(R.id.history_textview) TextView historyTextView;
    private ViolatorAdapter selfAdapter;

    public ViolatorHeaderViewHolder(View itemView) {
      super(itemView);
    }

    @OnClick({ R.id.turn_textview, R.id.sido_textview, R.id.history_textview }) void onSortClick(
        View view) {
      switch (view.getId()) {
        case R.id.turn_textview:
          selfAdapter.remove(0);
          selfAdapter.sortTurn();
          selfAdapter.add(0, new Violator());
          break;
        case R.id.sido_textview:
          selfAdapter.remove(0);
          selfAdapter.sortSido();
          selfAdapter.add(0, new Violator());
          break;
        case R.id.history_textview:
          selfAdapter.remove(0);
          selfAdapter.sortHistory();
          selfAdapter.add(0, new Violator());
          break;
      }
      selfAdapter.refresh();
    }
  }
}
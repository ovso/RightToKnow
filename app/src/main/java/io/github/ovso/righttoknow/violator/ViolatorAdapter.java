package io.github.ovso.righttoknow.violator;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
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

  private List<Violator> items = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new ViolatorViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_violator_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder baseHolder, int position) {
    if (baseHolder instanceof ViolatorViewHolder) {
      ViolatorViewHolder holder = (ViolatorViewHolder) baseHolder;
      Violator violator = items.get(position);
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
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  @Override public void add(Violator item) {
    items.add(item);
  }

  @Override public void addAll(List<Violator> items) {
    this.items.addAll(items);
  }

  @Override public Violator remove(int position) {
    return items.remove(position);
  }

  @Override public Violator getItem(int position) {
    return items.get(position);
  }

  @Override public void add(int index, Violator item) {
    items.add(index, item);
  }

  @Override public int getSize() {
    return items.size();
  }

  @Setter private OnRecyclerItemClickListener onRecyclerItemClickListener;

  private void sortTurn() {
    Comparator<Violator> comparator = (t1, t2) -> Integer.valueOf(t2.getReg_number())
        .compareTo(Integer.valueOf(t1.getReg_number()));
    Collections.sort(items, comparator);
  }

  private void sortSido() {
    Comparator<Violator> comparator = (t1, t2) -> t1.getSido().compareTo(t2.getSido());
    Collections.sort(items, comparator);
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
    items.clear();
  }

}

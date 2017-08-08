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
import io.github.ovso.righttoknow.violator.vo.Violator;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 3
 */

public class ViolatorAdapter extends BaseRecyclerAdapter
    implements BaseAdapterView, ViolatorAdapterDataModel<Violator> {

  private List<Violator> violators = new ArrayList<>();

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
      Violator violator = violators.get(position);
      holder.turnTextview.setText(violator.getTurn());
      holder.sidoTextView.setText(violator.getSido());
      holder.sigunguTextView.setText(violator.getSigungu());
      holder.violatorTextView.setText(violator.getViolator());
      holder.centerNameTextView.setText(violator.getName());
      holder.historyTextView.setText(violator.getHistory());

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
    violators.add(item);
  }

  @Override public void addAll(List<Violator> items) {
    violators.addAll(items);
  }

  @Override public Violator remove(int position) {
    return null;
  }

  @Override public Violator getItem(int position) {
    return violators.get(position);
  }

  @Override public int getSize() {
    return violators.size();
  }

  @Setter private OnRecyclerItemClickListener onRecyclerItemClickListener;

  @Override public int getItemViewType(int position) {
    if (TextUtils.isEmpty(violators.get(position).getTurn())) {
      return ITEM_VIEW_TYPE_HEADER;
    } else {
      return ITEM_VIEW_TYPE_DEFAULT;
    }
  }

  @Override public void sortTurn() {

  }

  @Override public void sortSido() {

  }

  @Override public void sortHistory() {

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

  final static class ViolatorHeaderViewHolder extends BaseRecyclerAdapter.BaseViewHolder {
    public ViolatorHeaderViewHolder(View itemView) {
      super(itemView);
    }
  }

  @Override public void clear() {
    violators.clear();
  }
}

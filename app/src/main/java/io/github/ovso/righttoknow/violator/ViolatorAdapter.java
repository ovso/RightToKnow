package io.github.ovso.righttoknow.violator;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.common.Utility;
import io.github.ovso.righttoknow.violator.model.Violator;
import java.util.ArrayList;
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
      holder.itemView.setOnClickListener(view -> onRecyclerItemClickListener.onItemClick(violator));
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

  @Setter private OnRecyclerItemClickListener<Violator> onRecyclerItemClickListener;

  final static class ViolatorViewHolder extends BaseRecyclerAdapter.BaseViewHolder {
    @BindView(R.id.turn_textview) TextView turnTextview;
    @BindView(R.id.sido_textview) TextView sidoTextView;
    @BindView(R.id.sigungu_textview) TextView sigunguTextView;
    @BindView(R.id.violator_textview) TextView violatorTextView;
    @BindView(R.id.name_textview) TextView centerNameTextView;

    public ViolatorViewHolder(View itemView) {
      super(itemView);
    }
  }

  @Override public void clear() {
    items.clear();
  }
}

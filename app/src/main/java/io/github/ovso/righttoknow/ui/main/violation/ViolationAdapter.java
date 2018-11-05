package io.github.ovso.righttoknow.ui.main.violation;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.network.model.violation.Violation;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class ViolationAdapter extends BaseRecyclerAdapter
    implements BaseAdapterView, BaseAdapterDataModel<Violation> {

  private List<Violation> items = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new ViolationFacilityViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_violation_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder baseHolder, int position) {
    if (baseHolder instanceof ViolationFacilityViewHolder) {
      ViolationFacilityViewHolder holder = (ViolationFacilityViewHolder) baseHolder;
      final Violation v = items.get(position);
      holder.orderTextView.setText(String.valueOf(v.order));
      holder.sidoTextView.setText(v.sido);
      holder.sigunguTextView.setText(v.sigungu);
      holder.facNameTextView.setText(v.vio_ccc);
      holder.typeTextView.setText(v.type);
      holder.itemView.setOnClickListener(view -> onRecyclerItemClickListener.onItemClick(v));
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  @Override public void add(Violation item) {
    items.add(item);
  }

  @Override public void addAll(List<Violation> items) {
    this.items.addAll(items);
  }

  @Override public Violation remove(int position) {
    return items.remove(position);
  }

  @Override public Violation getItem(int position) {
    return items.get(position);
  }

  @Override public void add(int index, Violation item) {
    items.add(index, item);
  }

  @Override public int getSize() {
    return items.size();
  }

  @Override public void clear() {
    items.clear();
  }

  @Setter private OnRecyclerItemClickListener<Violation> onRecyclerItemClickListener;

  final static class ViolationFacilityViewHolder extends BaseViewHolder {
    @BindView(R.id.order_textview) TextView orderTextView;
    @BindView(R.id.sido_textview) TextView sidoTextView;
    @BindView(R.id.sigungu_textview) TextView sigunguTextView;
    @BindView(R.id.fac_name_textview) TextView facNameTextView;
    @BindView(R.id.type_textview) TextView typeTextView;

    public ViolationFacilityViewHolder(View itemView) {
      super(itemView);
    }
  }
}
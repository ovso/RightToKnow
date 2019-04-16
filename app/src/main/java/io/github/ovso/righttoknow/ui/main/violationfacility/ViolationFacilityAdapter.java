package io.github.ovso.righttoknow.ui.main.violationfacility;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.ui.main.violationfacility.model.VioFac;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class ViolationFacilityAdapter extends BaseRecyclerAdapter
    implements BaseAdapterView, BaseAdapterDataModel<VioFac> {

  private List<VioFac> items = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new ViolationFacilityViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_violation_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder baseHolder, int position) {
    if (baseHolder instanceof ViolationFacilityViewHolder) {
      ViolationFacilityViewHolder holder = (ViolationFacilityViewHolder) baseHolder;
      final VioFac fac = items.get(position);
      holder.orderTextView.setText(String.valueOf(fac.getOrder()));
      holder.sidoTextView.setText(fac.getSido());
      holder.sigunguTextView.setText(fac.getSigungu());
      holder.facNameTextView.setText(fac.getFac_name());
      holder.typeTextView.setText(fac.getType());
      holder.itemView.setOnClickListener(view -> onRecyclerItemClickListener.onItemClick(fac));
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  @Override public void add(VioFac item) {
    items.add(item);
  }

  @Override public void addAll(List<VioFac> items) {
    this.items.addAll(items);
  }

  @Override public VioFac remove(int position) {
    return items.remove(position);
  }

  @Override public VioFac getItem(int position) {
    return items.get(position);
  }

  @Override public void add(int index, VioFac item) {
    items.add(index, item);
  }

  @Override public int getSize() {
    return items.size();
  }

  @Override public void clear() {
    items.clear();
  }

  @Setter private OnRecyclerItemClickListener<VioFac> onRecyclerItemClickListener;

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
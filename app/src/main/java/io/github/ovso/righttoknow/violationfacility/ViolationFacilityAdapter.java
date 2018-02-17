package io.github.ovso.righttoknow.violationfacility;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.common.Utility;
import io.github.ovso.righttoknow.violationfacility.model.ViolationFacility;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationFacilityAdapter extends BaseRecyclerAdapter
    implements BaseAdapterView, FacilityAdapterDataModel<ViolationFacility> {

  private List<ViolationFacility> items = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new ViolationFacilityViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_violation_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder baseHolder, int position) {
    if (baseHolder instanceof ViolationFacilityViewHolder) {
      ViolationFacilityViewHolder holder = (ViolationFacilityViewHolder) baseHolder;
      ViolationFacility fac = items.get(position);
      holder.turnTextview.setText(String.valueOf(fac.getReg_number()+1));
      holder.sidoTextView.setText(fac.getSido());
      holder.sigunguTextView.setText(fac.getSigungu());
      holder.nameTextView.setText(fac.getOld_fac_name() + Utility.getActionEmoji(fac.getAction()));
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

  @Override public void add(ViolationFacility item) {
    items.add(item);
  }

  @Override public void addAll(List<ViolationFacility> items) {
    this.items.addAll(items);
  }

  @Override public ViolationFacility remove(int position) {
    return items.remove(position);
  }

  @Override public ViolationFacility getItem(int position) {
    return items.get(position);
  }

  @Override public void add(int index, ViolationFacility item) {
    items.add(index, item);
  }

  @Override public int getSize() {
    return items.size();
  }

  @Override public void clear() {
    items.clear();
  }

  @Setter private OnRecyclerItemClickListener onRecyclerItemClickListener;

  final static class ViolationFacilityViewHolder extends BaseRecyclerAdapter.BaseViewHolder {
    @BindView(R.id.turn_textview) TextView turnTextview;
    @BindView(R.id.sido_textview) TextView sidoTextView;
    @BindView(R.id.sigungu_textview) TextView sigunguTextView;
    @BindView(R.id.name_textview) TextView nameTextView;
    @BindView(R.id.type_textview) TextView typeTextView;

    public ViolationFacilityViewHolder(View itemView) {
      super(itemView);
    }
  }
}
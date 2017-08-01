package io.github.ovso.righttoknow.violationfacility;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationFacilityAdapter extends BaseRecyclerAdapter
    implements BaseAdapterView, BaseAdapterDataModel<ViolationFacility> {
  private List<ViolationFacility> violationFacilities = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view) {
    return new ViolationFacilityViewHolder(view);
  }

  @Override public int getLayoutRes() {
    return R.layout.fragment_violation_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder baseHolder, int position) {
    if (baseHolder instanceof ViolationFacilityViewHolder) {
      ViolationFacilityViewHolder holder = (ViolationFacilityViewHolder) baseHolder;
      ViolationFacility violationFacility = violationFacilities.get(position);
      holder.turnTextview.setText(violationFacility.getTurn());
      holder.sidoTextView.setText(violationFacility.getSido());
      holder.sigunguTextView.setText(violationFacility.getSigungu());
      holder.nameTextView.setText(violationFacility.getName());
      holder.typeTextView.setText(violationFacility.getType());
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  @Override public void add(ViolationFacility item) {
    violationFacilities.add(item);
  }

  @Override public void addAll(List<ViolationFacility> items) {
    violationFacilities.addAll(items);
  }

  @Override public ViolationFacility remove(int position) {
    return null;
  }

  @Override public ViolationFacility getItem(int position) {
    return null;
  }

  @Override public int getSize() {
    return violationFacilities.size();
  }

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
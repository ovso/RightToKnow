package io.github.ovso.righttoknow.violationfacility;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationFacilityAdapter extends BaseRecyclerAdapter
    implements BaseAdapterView, BaseAdapterDataModel<ViolationFacility> {

  private List<ViolationFacility> violationFacilities = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    if (viewType == ITEM_VIEW_TYPE_HEADER) {
      return new ViolationFacilityHeaderViewHolder(view);
    } else {
      return new ViolationFacilityViewHolder(view);
    }
  }

  @Override public int getLayoutRes(int viewType) {
    if(viewType == ITEM_VIEW_TYPE_DEFAULT) {
      return R.layout.fragment_violation_item;
    } else {
      return R.layout.fragment_violation_item_header;
    }

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

      holder.violationFacility = violationFacility;

      holder.onRecyclerItemClickListener = onRecyclerItemClickListener;
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

  @Override public void clear() {
    violationFacilities.clear();
  }

  @Setter private OnRecyclerItemClickListener onRecyclerItemClickListener;

  @Override public int getItemViewType(int position) {
    if (position < 1) {
      return ITEM_VIEW_TYPE_HEADER;
    } else {
      return ITEM_VIEW_TYPE_DEFAULT;
    }
  }

  final static class ViolationFacilityViewHolder extends BaseRecyclerAdapter.BaseViewHolder {
    @BindView(R.id.turn_textview) TextView turnTextview;
    @BindView(R.id.sido_textview) TextView sidoTextView;
    @BindView(R.id.sigungu_textview) TextView sigunguTextView;
    @BindView(R.id.name_textview) TextView nameTextView;
    @BindView(R.id.type_textview) TextView typeTextView;
    OnRecyclerItemClickListener onRecyclerItemClickListener;
    ViolationFacility violationFacility;

    public ViolationFacilityViewHolder(View itemView) {
      super(itemView);
    }

    @OnClick(R.id.container_view) void onItemClick() {
      onRecyclerItemClickListener.onItemClick(violationFacility);
    }
  }

  final static class ViolationFacilityHeaderViewHolder extends BaseRecyclerAdapter.BaseViewHolder {
    public ViolationFacilityHeaderViewHolder(View itemView) {
      super(itemView);
    }
  }
}
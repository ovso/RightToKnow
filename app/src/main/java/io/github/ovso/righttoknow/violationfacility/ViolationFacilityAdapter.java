package io.github.ovso.righttoknow.violationfacility;

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
import io.github.ovso.righttoknow.violationfacility.model.ViolationFacility;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
      holder.turnTextview.setText(String.valueOf(fac.getReg_number()));
      holder.sidoTextView.setText(fac.getSido());
      holder.sigunguTextView.setText(fac.getSigungu());

      holder.nameTextView.setText(fac.getOld_fac_name() + Utility.getActionEmoji(fac.getAction()));
      holder.typeTextView.setText(fac.getType());

      holder.violationFacility = fac;

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

  @Override public int getItemViewType(int position) {
    int regNumber = items.get(position).getReg_number();
    if (regNumber > 0) {
      return ITEM_VIEW_TYPE_DEFAULT;
    } else {
      return ITEM_VIEW_TYPE_HEADER;
    }
  }

  private void sortRegNumber() {
    Comparator<ViolationFacility> comparator = (t1, t2) -> Integer.valueOf(t2.getReg_number())
        .compareTo(Integer.valueOf(t1.getReg_number()));
    Collections.sort(items, comparator);
  }

  private void sortSido() {
    Comparator<ViolationFacility> comparator = (t1, t2) -> t1.getSido().compareTo(t2.getSido());
    Collections.sort(items, comparator);
  }

  private void sortType() {
    Comparator<ViolationFacility> comparator = (t1, t2) -> t1.getType().compareTo(t2.getType());
    Collections.sort(items, comparator);
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

    List<ViolationFacility> temps = new ArrayList<>();
    for (ViolationFacility v : items) {
      String sigungu = v.getSigungu();
      if (!TextUtils.isEmpty(sigungu)) {
        if (sigungu.indexOf(nowLocality) != -1) {
          temps.add(v);
        }
      }
    }
    items.clear();
    items.addAll(temps);
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

  @Override public void searchAllWords(String query) {
    List<ViolationFacility> returnItems = new ArrayList<>();

    items.clear();
    if (items.size() > 0) {
      items.remove(0);
    }
    for (int i = 0; i < items.size(); i++) {
      ViolationFacility item = items.get(i);
      String trimQuery = query.trim();
      if (item.getSido().contains(trimQuery)
          || item.getSigungu().contains(trimQuery)
          || item.getType().contains(trimQuery)
          || item.getOld_fac_name().contains(trimQuery)
          || item.getNow_fac_name().contains(trimQuery)
          || item.getOld_boss().contains(trimQuery)
          || item.getNow_boss().contains(trimQuery)
          || item.getOld_director().contains(trimQuery)
          || item.getNow_director().contains(trimQuery)
          || item.getAddress().contains(trimQuery)) {
        returnItems.add(item);
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
    items.clear();
    items.addAll(returnItems);
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
}
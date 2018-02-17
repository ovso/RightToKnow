package io.github.ovso.righttoknow.certified;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.certified.model.ChildCertified;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 3
 */

public class CertifiedAdapter extends BaseRecyclerAdapter
    implements BaseAdapterView, BaseAdapterDataModel<ChildCertified> {

  private List<ChildCertified> certifieds = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new CertifiedViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_certified_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder baseHolder, int position) {
    if (baseHolder instanceof CertifiedViewHolder) {
      CertifiedViewHolder holder = (CertifiedViewHolder) baseHolder;
      ChildCertified certified = certifieds.get(position);
      holder.certified = certified;
      holder.titleTextview.setText(certified.getTitle());
      holder.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  @Override public void add(ChildCertified item) {
    certifieds.add(item);
  }

  @Override public void addAll(List<ChildCertified> items) {
    certifieds.addAll(items);
  }

  @Override public ChildCertified remove(int position) {
    return certifieds.remove(position);
  }

  @Override public ChildCertified getItem(int position) {
    return certifieds.get(position);
  }

  @Override public void add(int index, ChildCertified item) {
    certifieds.add(index, item);
  }

  @Override public int getSize() {
    return certifieds.size();
  }

  @Setter private OnRecyclerItemClickListener onRecyclerItemClickListener;

  final static class CertifiedViewHolder extends BaseViewHolder {
    @BindView(R.id.title_textview) TextView titleTextview;
    OnRecyclerItemClickListener onRecyclerItemClickListener;
    ChildCertified certified;

    public CertifiedViewHolder(View itemView) {
      super(itemView);
    }

    @OnClick(R.id.container_view) void onItemClick() {
      onRecyclerItemClickListener.onItemClick(certified);
    }
  }

  @Override public void clear() {
    certifieds.clear();
  }
}

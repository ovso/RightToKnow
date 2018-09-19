package io.github.ovso.righttoknow.ui.main.certified;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.jakewharton.rxbinding2.view.RxView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.ui.main.certified.model.ChildCertified;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.framework.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.framework.adapter.OnRecyclerItemClickListener;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 3
 */

public class CertifiedAdapter extends BaseRecyclerAdapter
    implements BaseAdapterView, BaseAdapterDataModel<ChildCertified> {

  private List<ChildCertified> items = new ArrayList<>();

  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new CertifiedViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return R.layout.fragment_certified_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder baseHolder, int position) {
    if (baseHolder instanceof CertifiedViewHolder) {
      CertifiedViewHolder holder = (CertifiedViewHolder) baseHolder;
      final ChildCertified item = items.get(position);

      holder.titleTextview.setText(item.getTitle().trim().replace(" ", "\u00A0"));
      holder.orderTextview.setText(String.valueOf(item.getOrder()));
      //holder.itemView.setOnClickListener(view -> onRecyclerItemClickListener.onItemClick(item));
      compositeDisposable.add(RxView.clicks(holder.itemView)
          .throttleFirst(1, TimeUnit.SECONDS)
          .subscribe(new Consumer<Object>() {
            @Override public void accept(Object o) throws Exception {
              onRecyclerItemClickListener.onItemClick(item);
            }
          }));
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  @Override public void add(ChildCertified item) {
    items.add(item);
  }

  @Override public void addAll(List<ChildCertified> items) {
    this.items.addAll(items);
  }

  @Override public ChildCertified remove(int position) {
    return items.remove(position);
  }

  @Override public ChildCertified getItem(int position) {
    return items.get(position);
  }

  @Override public void add(int index, ChildCertified item) {
    items.add(index, item);
  }

  @Override public int getSize() {
    return items.size();
  }

  @Override public void clear() {
    items.clear();
  }
  @Setter private OnRecyclerItemClickListener<ChildCertified> onRecyclerItemClickListener;

  public void onDestroyView() {
    compositeDisposable.dispose();
    compositeDisposable.clear();
  }

  final static class CertifiedViewHolder extends BaseViewHolder {
    @BindView(R.id.title_textview) TextView titleTextview;
    @BindView(R.id.order_textview) TextView orderTextview;

    public CertifiedViewHolder(View itemView) {
      super(itemView);
    }
  }
}

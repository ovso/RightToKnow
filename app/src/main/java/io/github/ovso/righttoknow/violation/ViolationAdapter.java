package io.github.ovso.righttoknow.violation;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.adapter.BaseRecyclerAdapter;
import io.github.ovso.righttoknow.violation.vo.Violation;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationAdapter extends BaseRecyclerAdapter
    implements BaseAdapterView, BaseAdapterDataModel<Violation> {

  @Override protected BaseViewHolder createViewHolder(View view) {
    return new ViolationViewHolder(view);
  }

  @Override public int getLayoutRes() {
    return R.layout.fragment_violation_item;
  }

  @Override public void onBindViewHolder(BaseViewHolder baseHolder, int position) {
    if (baseHolder instanceof ViolationViewHolder) {
      ViolationViewHolder holder = (ViolationViewHolder) baseHolder;
      holder.turnTextview.setText("87");
      holder.sidoTextView.setText("대전광역시");
      holder.sigunguTextView.setText("대덕구");
      holder.nameTextView.setText("어린이집어린이집어린");
      holder.typeTextView.setText("가정");
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  @Override public void add(Violation item) {

  }

  @Override public void addAll(List<Violation> items) {

  }

  @Override public Violation remove(int position) {
    return null;
  }

  @Override public Violation getItem(int position) {
    return null;
  }

  @Override public int getSize() {
    return 100;
  }

  final static class ViolationViewHolder extends BaseRecyclerAdapter.BaseViewHolder {
    @BindView(R.id.turn_textview) TextView turnTextview;
    @BindView(R.id.sido_textview) TextView sidoTextView;
    @BindView(R.id.sigungu_textview) TextView sigunguTextView;
    @BindView(R.id.name_textview) TextView nameTextView;
    @BindView(R.id.type_textview) TextView typeTextView;

    public ViolationViewHolder(View itemView) {
      super(itemView);
    }
  }
}
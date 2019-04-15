package io.github.ovso.righttoknow.framework.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public abstract class BaseRecyclerAdapter
    extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder> {

  protected final static int ITEM_VIEW_TYPE_HEADER = 0;
  protected static final int ITEM_VIEW_TYPE_DEFAULT = 1;

  @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(viewType), parent, false);
    return createViewHolder(view, viewType);
  }

  protected abstract BaseViewHolder createViewHolder(View view, int viewType);

  @LayoutRes public abstract int getLayoutRes(int viewType);

  @Override public abstract void onBindViewHolder(BaseViewHolder holder, int position);

  @Override public abstract int getItemCount();

  public static class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

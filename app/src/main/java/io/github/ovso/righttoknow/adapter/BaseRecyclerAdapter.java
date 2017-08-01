package io.github.ovso.righttoknow.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

/**
 * Created by jaeho on 2017. 8. 1
 */

public abstract class BaseRecyclerAdapter
    extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder> {
  @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), parent, false);
    return createViewHolder(view);
  }

  protected abstract BaseViewHolder createViewHolder(View view);

  @LayoutRes public abstract int getLayoutRes();

  @Override public abstract void onBindViewHolder(BaseViewHolder holder, int position);

  @Override public abstract int getItemCount();

  public static class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

package io.github.ovso.righttoknow.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.ovso.righttoknow.common.ObjectUtils;

/**
 * Created by jaeho on 2017. 8. 1
 */

public abstract class BaseFragment extends Fragment {
  private Unbinder unbinder;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    if (ObjectUtils.isEmpty(inflater)) return null;
    View view = inflater.inflate(getLayoutResId(), container, false);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    unbinder = ButterKnife.bind(this, view);
  }

  @LayoutRes public abstract int getLayoutResId();

  @Override public void onDetach() {
    super.onDetach();
    unbinder.unbind();
  }
}

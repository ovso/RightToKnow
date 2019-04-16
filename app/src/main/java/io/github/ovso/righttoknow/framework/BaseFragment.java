package io.github.ovso.righttoknow.framework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.ovso.righttoknow.framework.utils.ObjectUtils;

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

package io.github.ovso.righttoknow.violator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.fragment.BaseFragment;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class ViolatorFragment extends BaseFragment implements ViolatorFragmentPresenter.View {
  private ViolatorFragmentPresenter presenter;
  public static ViolatorFragment newInstance(Bundle args) {
    ViolatorFragment f = new ViolatorFragment();
    f.setArguments(args);
    return f;
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_wrongdoer;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new ViolatorFragmentPresenterImpl(this);
    presenter.onActivityCreate(savedInstanceState);
  }

  @Override public void hideLoading() {

  }

  @Override public void showLoading() {

  }
}
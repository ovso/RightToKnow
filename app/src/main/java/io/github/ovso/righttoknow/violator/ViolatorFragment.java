package io.github.ovso.righttoknow.violator;

import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.fragment.BaseFragment;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class ViolatorFragment extends BaseFragment {
  public static ViolatorFragment newInstance(Bundle args) {
    ViolatorFragment f = new ViolatorFragment();
    f.setArguments(args);
    return f;
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_wrongdoer;
  }
}
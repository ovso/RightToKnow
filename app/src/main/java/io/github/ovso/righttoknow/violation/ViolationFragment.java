package io.github.ovso.righttoknow.violation;

import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.fragment.BaseFragment;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class ViolationFragment extends BaseFragment {

  public static ViolationFragment newInstance(Bundle args) {
    ViolationFragment f = new ViolationFragment();
    f.setArguments(args);
    return f;
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_violation;
  }
}
package io.github.ovso.righttoknow.wrongdoer;

import android.os.Bundle;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.fragment.BaseFragment;

/**
 * Created by jaeho on 2017. 7. 31
 */

public class WrongdoerFragment extends BaseFragment {
  public static WrongdoerFragment newInstance(Bundle args) {
    WrongdoerFragment f = new WrongdoerFragment();
    f.setArguments(args);
    return f;
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_wrongdoer;
  }
}
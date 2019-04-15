package io.github.ovso.righttoknow.framework;

import android.content.Context;
import dagger.android.support.AndroidSupportInjection;

public abstract class DaggerFragment extends BaseFragment {

  @Override public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }
}

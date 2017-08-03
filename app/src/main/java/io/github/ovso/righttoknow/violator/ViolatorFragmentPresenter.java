package io.github.ovso.righttoknow.violator;

import android.os.Bundle;

/**
 * Created by jaeho on 2017. 8. 3
 */

public interface ViolatorFragmentPresenter {

  void onActivityCreate(Bundle savedInstanceState);

  interface View {

    void hideLoading();

    void showLoading();
  }
}

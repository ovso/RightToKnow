package io.github.ovso.righttoknow.settings;

import android.os.Bundle;
import android.support.annotation.XmlRes;

/**
 * Created by jaeho on 2017. 9. 15
 */

public interface SettingsFragmentPresenter {

  void onCreate(Bundle savedInstanceState);

  interface View {

    void setContentView(@XmlRes int resId);
  }
}
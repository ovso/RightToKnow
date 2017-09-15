package io.github.ovso.righttoknow.settings;

import android.os.Bundle;
import android.support.annotation.XmlRes;

/**
 * Created by jaeho on 2017. 9. 15
 */

public interface SettingsFragmentPresenter {

  void onCreate(Bundle savedInstanceState);

  boolean onPreferenceClick();

  void onResume();

  interface View {

    void setContentView(@XmlRes int resId);

    void navigateToSettingsNotifications26();

    void navigateToSettingsNotifications21();

    void navigateToSettingsNotifications();

    void setNotifications(boolean enable);

    void setListener();
  }
}
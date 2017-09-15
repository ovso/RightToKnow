package io.github.ovso.righttoknow.settings;

import android.os.Bundle;
import io.github.ovso.righttoknow.R;

/**
 * Created by jaeho on 2017. 9. 15
 */

public class SettingsFragmentPresenterImpl implements SettingsFragmentPresenter {

  private SettingsFragmentPresenter.View view;

  SettingsFragmentPresenterImpl(SettingsFragmentPresenter.View view) {
    this.view = view;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    view.setContentView(R.xml.settings);
  }
}
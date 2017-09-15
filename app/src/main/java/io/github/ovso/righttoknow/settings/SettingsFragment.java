package io.github.ovso.righttoknow.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;

/**
 * Created by jaeho on 2017. 9. 15
 */

public class SettingsFragment extends PreferenceFragment implements SettingsFragmentPresenter.View {
  private SettingsFragmentPresenter presenter;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = new SettingsFragmentPresenterImpl(this);
    presenter.onCreate(savedInstanceState);
  }

  @Override public void setContentView(@XmlRes int resId) {
    addPreferencesFromResource(resId);
  }
}
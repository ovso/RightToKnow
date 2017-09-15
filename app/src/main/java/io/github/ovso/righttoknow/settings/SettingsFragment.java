package io.github.ovso.righttoknow.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import io.github.ovso.righttoknow.R;

/**
 * Created by jaeho on 2017. 9. 15
 */

public class SettingsFragment extends PreferenceFragment {
  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.settings);
  }
}

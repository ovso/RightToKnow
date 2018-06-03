package io.github.ovso.righttoknow.settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.model.Notices;
import io.github.ovso.righttoknow.R;

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

  @Override public void setListener() {
    findPreference(getString(R.string.key_opensource_license)).setOnPreferenceClickListener(
        new Preference.OnPreferenceClickListener() {
          @Override public boolean onPreferenceClick(Preference preference) {
            return presenter.onOpensourceClick();
          }
        });
  }

  @Override public void showOpenSourceLicenseDialog(Notices notices) {
    new LicensesDialog.Builder(getActivity()).setNotices(notices)
        .setIncludeOwnLicense(true)
        .build()
        .show();
  }

  @Override public void onResume() {
    super.onResume();
    presenter.onResume();
  }
}
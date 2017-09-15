package io.github.ovso.righttoknow.settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by jaeho on 2017. 9. 15
 */

public class SettingsFragment extends PreferenceFragment implements SettingsFragmentPresenter.View {
  private SettingsFragmentPresenter presenter;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = new SettingsFragmentPresenterImpl(this);
    presenter.onCreate(savedInstanceState);
    boolean enable = NotificationManagerCompat.from(getActivity().getApplicationContext()).areNotificationsEnabled();
  }

  @Override public void setContentView(@XmlRes int resId) {
    addPreferencesFromResource(resId);

    Preference preference = findPreference("notifications_key");
    preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      @Override public boolean onPreferenceClick(Preference preference) {
        //Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        //Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);

        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
        startActivity(intent);
        return false;
      }
    });
  }
}
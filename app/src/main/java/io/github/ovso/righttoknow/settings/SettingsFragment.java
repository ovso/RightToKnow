package io.github.ovso.righttoknow.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import hugo.weaving.DebugLog;

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
    notificationsSwitchPreference = (SwitchPreference) findPreference("notifications_key");
  }

  private SwitchPreference notificationsSwitchPreference;

  @Override public void setListener() {
    notificationsSwitchPreference.setOnPreferenceClickListener(
        preference1 -> presenter.onPreferenceClick());
  }

  @Override public void navigateToSettingsNotifications26() {
    Intent intent = new Intent();
    intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
    intent.putExtra("android.provider.extra.APP_PACKAGE", getActivity().getPackageName());
    startActivity(intent);
  }

  @Override public void navigateToSettingsNotifications21() {
    Intent intent = new Intent();
    intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
    intent.putExtra("app_package", getActivity().getPackageName());
    intent.putExtra("app_uid", getActivity().getApplicationInfo().uid);
    startActivity(intent);
  }

  @Override public void navigateToSettingsNotifications() {
    Intent intent = new Intent();
    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    intent.addCategory(Intent.CATEGORY_DEFAULT);
    intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
    startActivity(intent);
  }

  @DebugLog @Override public void setNotifications(boolean enable) {
    notificationsSwitchPreference.setChecked(enable);
  }

  @Override public void onResume() {
    super.onResume();
    presenter.onResume();
  }
}
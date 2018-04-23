package io.github.ovso.righttoknow.settings;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.framework.utils.Constants;
import io.github.ovso.righttoknow.framework.utils.Utility;

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
    view.setListener();
  }

  @Override public boolean onNotificationsClick() {
    if (Utility.getBuildVersion() > Build.VERSION_CODES.N_MR1) {
      view.navigateToSettingsNotifications26();
    } else if (Utility.getBuildVersion() >= Build.VERSION_CODES.LOLLIPOP) {
      view.navigateToSettingsNotifications21();
    } else {
      view.navigateToSettingsNotifications();
    }
    return false;
  }

  @Override public boolean onOpensourceClick() {
    view.showOpenSourceLicenseDialog(Constants.getNotices());
    return false;
  }

  @Override public void onResume() {
    boolean enable =
        NotificationManagerCompat.from(MyApplication.getInstance().getApplicationContext())
            .areNotificationsEnabled();
    view.setNotifications(enable);
  }
}
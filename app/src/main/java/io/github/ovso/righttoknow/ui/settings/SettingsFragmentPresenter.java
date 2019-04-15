package io.github.ovso.righttoknow.ui.settings;

import android.os.Bundle;
import androidx.annotation.XmlRes;
import de.psdev.licensesdialog.model.Notices;

/**
 * Created by jaeho on 2017. 9. 15
 */

public interface SettingsFragmentPresenter {

  void onCreate(Bundle savedInstanceState);

  void onResume();

  boolean onOpensourceClick();

  interface View {

    void setContentView(@XmlRes int resId);

    void setListener();

    void showOpenSourceLicenseDialog(Notices notices);
  }
}
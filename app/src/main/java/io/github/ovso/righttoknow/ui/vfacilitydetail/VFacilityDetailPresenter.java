package io.github.ovso.righttoknow.ui.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.StringRes;

/**
 * Created by jaeho on 2017. 8. 2
 */

public interface VFacilityDetailPresenter {

  void onCreate(Bundle savedInstanceState, Intent intent);

  void onDestroy();

  void onMapClick(Intent intent);

  void onRefresh(Intent intent);

  interface View {

    void setSupportActionBar();

    void showContents(String contents);

    void setListener();

    void setTitle(@StringRes int resId);

    void showLoading();

    void hideLoading();

    void showMessage(@StringRes int resId);

    void navigateToMap(double[] locations, String facName);

    void hideButton();

    void showButton();

    void finish();
  }
}

package io.github.ovso.righttoknow.ui.violator_contents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;

public interface ViolatorContentsPresenter {

  void onCreate(Bundle savedInstanceState, Intent intent);

  void onDestroy();

  void onMapClick();

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

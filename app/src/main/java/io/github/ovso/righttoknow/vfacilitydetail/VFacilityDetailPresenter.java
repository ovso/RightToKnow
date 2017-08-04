package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by jaeho on 2017. 8. 2
 */

public interface VFacilityDetailPresenter {

  void onCreate(Bundle savedInstanceState, Intent intent);

  void onRefresh();

  void onBackPressed();

  void onDestroy();

  interface View {

    void hideLoading();

    void showLoading();

    void setSupportActionBar();

    void showContents(String contents);

    void setListener();

    void showSnackbar(String msg);

    void setTitle(String title);
  }
}

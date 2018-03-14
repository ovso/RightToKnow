package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by jaeho on 2017. 8. 2
 */

public interface VFacilityDetailPresenter {

  void onCreate(Bundle savedInstanceState, Intent intent);

  void onBackPressed();

  void onDestroy();

  void onLocationClick();

  interface View {

    void setSupportActionBar();

    void showContents(String contents);

    void setListener();

    void setTitle(String title);

    void showAd();
  }
}

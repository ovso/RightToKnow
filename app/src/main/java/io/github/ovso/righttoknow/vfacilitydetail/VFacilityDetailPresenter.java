package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by jaeho on 2017. 8. 2
 */

public interface VFacilityDetailPresenter {

  void onCreate(Bundle savedInstanceState, Intent intent);

  interface View {

    void hideLoading();

    void showLoading();

    void setSupportActionBar();

    void showContents(String contents);

    void showNoContents();
  }
}

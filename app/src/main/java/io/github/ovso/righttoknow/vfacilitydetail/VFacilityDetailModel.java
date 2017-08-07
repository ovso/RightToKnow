package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.content.res.Resources;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.common.Constants;
import lombok.Getter;

/**
 * Created by jaeho on 2017. 8. 4
 */

class VFacilityDetailModel {
  @Getter private String link;
  @Getter private int from;
  @Getter private String title;

  public void setIntent(Intent intent) {
    Resources res = MyApplication.getInstance().getResources();

    link = Constants.BASE_URL + intent.getStringExtra("link");
    from = intent.getIntExtra("from", R.layout.fragment_violation);
    if (from == R.layout.fragment_violation) {
      title = res.getString(R.string.day_care_center) + " " + res.getString(
          R.string.title_vioation_facility_inquiry) + res.getString(R.string.detail);
    } else if (from == R.layout.fragment_violator) {
      title = res.getString(R.string.day_care_center) + " " + res.getString(
          R.string.title_violator_inquiry) + res.getString(R.string.detail);
    }
  }
}

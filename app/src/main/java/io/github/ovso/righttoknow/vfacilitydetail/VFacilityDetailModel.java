package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
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
    link = Constants.BASE_URL + intent.getStringExtra("link");
    from = intent.getIntExtra("from", R.layout.fragment_violation);
    if (from == R.layout.fragment_violation) {
      title = MyApplication.getInstance().getString(R.string.title_vioation_facility_inquiry)
          + "("
          + MyApplication.getInstance().getString(R.string.detail)
          + ")";
    } else if (from == R.layout.fragment_violator) {
      title = MyApplication.getInstance().getString(R.string.title_violator_inquiry)
          + "("
          + MyApplication.getInstance().getString(R.string.detail)
          + ")";
    }
  }
}

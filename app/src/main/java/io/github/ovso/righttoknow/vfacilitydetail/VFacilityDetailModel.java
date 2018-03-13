package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Context;
import android.content.res.Resources;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.violationfacility.model.ViolationFacility;
import io.github.ovso.righttoknow.violator.model.Violator;
import java.io.Serializable;

/**
 * Created by jaeho on 2017. 8. 4
 */

class VFacilityDetailModel {
  private Resources res;

  VFacilityDetailModel(Context context) {
    res = context.getResources();
  }

  public String getTitle(Serializable serializable) {
    if (serializable instanceof ViolationFacility) {
      return res.getString(R.string.day_care_center) + " " + res.getString(
          R.string.title_vioation_facility_inquiry) + res.getString(R.string.detail);
    } else if (serializable instanceof Violator) {
      return res.getString(R.string.day_care_center) + " " + res.getString(
          R.string.title_violator_inquiry) + res.getString(R.string.detail);
    } else {
      return res.getString(R.string.app_name);
    }
  }

  public String getContents(Serializable serializable) {
    if (serializable instanceof Violator) { // Violator ex
      return getViolatorContents((Violator) serializable);
    } else if (serializable instanceof ViolationFacility) {
      return getFacContents((ViolationFacility) serializable);
    } else {
      return "";
    }
  }

  private String getViolatorContents(Violator violator) {
    StringBuilder builder = new StringBuilder();
    //sido
    builder.append(res.getString(R.string.detail_sido)).append(violator.getSido());
    builder.append("\n\n");
    //sigungu
    builder.append(res.getString(R.string.detail_sigungu)).append(violator.getSigungu());
    builder.append("\n\n");
    //name
    builder.append(res.getString(R.string.detail_violator_name)).append(violator.getName());
    builder.append("\n\n");
    //old center name
    builder.append(res.getString(R.string.detail_vio_old_center_name))
        .append(violator.getFac_name());
    builder.append("\n\n");
    //history
    builder.append(res.getString(R.string.detail_violation_history)).append(violator.getHistory());
    builder.append("\n\n");
    //address
    builder.append(res.getString(R.string.detail_address)).append(violator.getAddress());
    builder.append("\n\n");
    //action
    //builder.append(res.getString(R.string.detail_violator_action)).append(violator.getAction());
    builder.append("\n");
    builder.append("\n");
    //disposal
    //builder.append(res.getString(R.string.detail_violator_disposal)).append(violator.getDisposal());
    builder.append("\n");
    builder.append("\n");
    return builder.toString();
  }

  private String getFacContents(ViolationFacility fac) {
    StringBuilder builder = new StringBuilder();
    //sido
    builder.append(res.getString(R.string.detail_sido)).append(fac.getSido());
    builder.append("\n\n");
    //sigungu
    builder.append(res.getString(R.string.detail_sigungu)).append(fac.getSigungu());
    builder.append("\n\n");
    //type
    builder.append(res.getString(R.string.detail_type)).append(fac.getType());
    builder.append("\n\n");
    //now
    builder.append(res.getString(R.string.detail_now));
    builder.append("\n");
    builder.append(res.getString(R.string.detail_daycare_center)).append(fac.getNow_fac_name());
    builder.append("\n");
    builder.append(res.getString(R.string.detail_boss)).append(fac.getNow_boss());
    builder.append("\n");
    builder.append(res.getString(R.string.detail_director)).append(fac.getNow_director());
    builder.append("\n\n");
    //old
    builder.append(res.getString(R.string.detail_vio_old));
    builder.append("\n");
    builder.append(res.getString(R.string.detail_daycare_center)).append(fac.getOld_fac_name());
    builder.append("\n");
    builder.append(res.getString(R.string.detail_boss)).append(fac.getOld_boss());
    builder.append("\n");
    builder.append(res.getString(R.string.detail_director)).append(fac.getOld_director());
    builder.append("\n\n");
    //address
    builder.append(res.getString(R.string.detail_address)).append(fac.getAddress());
    builder.append("\n\n");
    //action
    builder.append(res.getString(R.string.detail_vio_action));
    builder.append("\n");
    for (String action : fac.getAction()) {
      builder.append(action).append("\n");
    }
    builder.append("\n");
    //disposal
    builder.append(res.getString(R.string.detail_vio_disposal));
    builder.append("\n");
    for (String disposal : fac.getDisposal()) {
      builder.append(disposal).append("\n");
    }

    return builder.toString();
  }
}

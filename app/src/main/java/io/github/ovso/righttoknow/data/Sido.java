package io.github.ovso.righttoknow.data;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.utils.ResourceProvider;

public enum Sido {
  SIDO1(R.id.sido1, R.string.sido1), SIDO2(R.id.sido2, R.string.sido2), SIDO3(R.id.sido3,
      R.string.sido3), SIDO4(R.id.sido4, R.string.sido4), SIDO5(R.id.sido5, R.string.sido5), SIDO6(
      R.id.sido6, R.string.sido6), SIDO7(R.id.sido7, R.string.sido7), SIDO8(R.id.sido8,
      R.string.sido8), SIDO9(R.id.sido9, R.string.sido9), SIDO10(R.id.sido10,
      R.string.sido10), SIDO11(R.id.sido11, R.string.sido11), SIDO12(R.id.sido12,
      R.string.sido12), SIDO13(R.id.sido13, R.string.sido13), SIDO14(R.id.sido14,
      R.string.sido14), SIDO15(R.id.sido15, R.string.sido15), SIDO16(R.id.sido16,
      R.string.sido16), SIDO17(R.id.sido17, R.string.sido17);
  private @StringRes int resId;
  private @IdRes int itemId;

  private Sido(@IdRes int itemId, @StringRes int resId) {
    this.itemId = itemId;
    this.resId = resId;
  }

  public static String toName(@IdRes int itemId, ResourceProvider res) {
    for (Sido sido : Sido.values()) {
      if(sido.itemId == itemId) {
        return res.getString(sido.resId);
      }
    }
    return "";
  }

  public static String getSido(@IdRes int itemId, Context context) {
    Resources res = context.getResources();
    if (Sido.SIDO1.itemId == itemId) {
      return res.getString(Sido.SIDO1.resId);
    } else if (Sido.SIDO2.itemId == itemId) {
      return res.getString(Sido.SIDO2.resId);
    } else if (Sido.SIDO3.itemId == itemId) {
      return res.getString(Sido.SIDO3.resId);
    } else if (Sido.SIDO4.itemId == itemId) {
      return res.getString(Sido.SIDO4.resId);
    } else if (Sido.SIDO5.itemId == itemId) {
      return res.getString(Sido.SIDO5.resId);
    } else if (Sido.SIDO6.itemId == itemId) {
      return res.getString(Sido.SIDO6.resId);
    } else if (Sido.SIDO7.itemId == itemId) {
      return res.getString(Sido.SIDO7.resId);
    } else if (Sido.SIDO8.itemId == itemId) {
      return res.getString(Sido.SIDO8.resId);
    } else if (Sido.SIDO9.itemId == itemId) {
      return res.getString(Sido.SIDO9.resId);
    } else if (Sido.SIDO10.itemId == itemId) {
      return res.getString(Sido.SIDO10.resId);
    } else if (Sido.SIDO11.itemId == itemId) {
      return res.getString(Sido.SIDO11.resId);
    } else if (Sido.SIDO12.itemId == itemId) {
      return res.getString(Sido.SIDO12.resId);
    } else if (Sido.SIDO13.itemId == itemId) {
      return res.getString(Sido.SIDO13.resId);
    } else if (Sido.SIDO14.itemId == itemId) {
      return res.getString(Sido.SIDO14.resId);
    } else if (Sido.SIDO15.itemId == itemId) {
      return res.getString(Sido.SIDO15.resId);
    } else if (Sido.SIDO16.itemId == itemId) {
      return res.getString(Sido.SIDO16.resId);
    } else if (Sido.SIDO17.itemId == itemId) {
      return res.getString(Sido.SIDO17.resId);
    } else {
      return null;
    }
  }
}

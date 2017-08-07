package io.github.ovso.righttoknow.listener;

import android.support.annotation.IdRes;

/**
 * Created by jaeho on 2017. 8. 8
 */

public interface OnFragmentEventListener {
  void onSort(@IdRes int id);
  void onMyLocation(String loc);
}

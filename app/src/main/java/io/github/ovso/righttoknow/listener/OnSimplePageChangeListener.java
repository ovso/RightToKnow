package io.github.ovso.righttoknow.listener;

import android.support.v4.view.ViewPager;

/**
 * Created by jaeho on 2017. 8. 1
 */

public abstract class OnSimplePageChangeListener implements ViewPager.OnPageChangeListener {
  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override public void onPageSelected(int position) {
    onPageChanged(position);
  }

  @Override public void onPageScrollStateChanged(int state) {

  }

  public abstract void onPageChanged(int position);
}

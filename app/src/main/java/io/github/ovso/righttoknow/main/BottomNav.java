package io.github.ovso.righttoknow.main;

import io.github.ovso.righttoknow.certified.CertifiedFragment;
import io.github.ovso.righttoknow.news.NewsFragment;
import io.github.ovso.righttoknow.video.VideoFragment;
import io.github.ovso.righttoknow.violationfacility.ViolationFacilityFragment;
import io.github.ovso.righttoknow.violator.ViolatorFragment;

/**
 * Created by jaeho on 2018. 2. 17
 */

public enum BottomNav {
  LAYOUT_VIOLATION(ViolationFacilityFragment.class.getSimpleName()), LAYOUT_VIOLATOR(
      ViolatorFragment.class.getSimpleName()), LAYOUT_CERTIFIED(
      CertifiedFragment.class.getSimpleName()), LAYOUT_NEWS(
      NewsFragment.class.getSimpleName()), LAYOUT_VIDEO(VideoFragment.class.getSimpleName());

  private String value;

  private BottomNav(String value) {
    this.value = value;
  }
}

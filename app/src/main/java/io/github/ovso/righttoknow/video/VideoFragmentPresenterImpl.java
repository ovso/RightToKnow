package io.github.ovso.righttoknow.video;

import android.os.Bundle;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoFragmentPresenterImpl implements VideoFragmentPresenter {

  private VideoFragmentPresenter.View view;

  VideoFragmentPresenterImpl(VideoFragmentPresenter.View view) {
    this.view = view;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {

  }
}
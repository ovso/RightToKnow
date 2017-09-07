package io.github.ovso.righttoknow.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.fragment.BaseFragment;

/**
 * Created by jaeho on 2017. 9. 7
 */

public class VideoFragment extends BaseFragment implements VideoFragmentPresenter.View {

  private VideoFragmentPresenter presenter;

  public static VideoFragment newInstance(Bundle args) {
    VideoFragment f = new VideoFragment();
    f.setArguments(args);
    return f;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new VideoFragmentPresenterImpl(this);
    presenter.onActivityCreated(savedInstanceState);
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_video;
  }
}

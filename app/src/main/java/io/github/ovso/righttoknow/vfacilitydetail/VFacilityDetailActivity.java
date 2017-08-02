package io.github.ovso.righttoknow.vfacilitydetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.main.BaseActivity;

/**
 * Created by jaeho on 2017. 8. 2
 */

public class VFacilityDetailActivity extends BaseActivity implements VFacilityDetailPresenter.View {

  VFacilityDetailPresenter presenter;
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = new VFacilityDetailPresenterImpl(this);
    presenter.onCreate(savedInstanceState, getIntent());
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_vfacilitydetail;
  }

  @Override public void hideLoading() {

  }

  @Override public void showLoading() {

  }

  @Override public void setSupportActionBar() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return true;
  }
}

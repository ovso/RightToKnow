package io.github.ovso.righttoknow.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.BaseActivity;

/**
 * Created by jaeho on 2017. 9. 15
 */

public class SettingsActivity extends BaseActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public void setSupportActionBar(@Nullable Toolbar toolbar) {
    super.setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(R.string.action_settings);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return true;
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_settings;
  }
}
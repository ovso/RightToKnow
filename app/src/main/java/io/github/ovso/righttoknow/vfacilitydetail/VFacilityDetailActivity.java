package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.main.BaseActivity;

/**
 * Created by jaeho on 2017. 8. 2
 */

public class VFacilityDetailActivity extends BaseActivity implements VFacilityDetailPresenter.View {

  VFacilityDetailPresenter presenter;
  @BindView(R.id.contents_textview) TextView contentsTextView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = new VFacilityDetailPresenterImpl(this);
    presenter.onCreate(savedInstanceState, getIntent());
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_vfacilitydetail;
  }

  @Override public void setSupportActionBar() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override public void showContents(String contents) {
    contentsTextView.setText(contents);
  }

  @Override public void setListener() {
  }

  @BindView(R.id.content_view) View contentView;

  @Override public void setTitle(String title) {
    getSupportActionBar().setTitle(title);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return true;
  }

  @OnClick(R.id.share_button) void onShareClick() {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_message));
    intent.putExtra(Intent.EXTRA_TEXT, contentsTextView.getText().toString());
    Intent chooser = Intent.createChooser(intent, getString(R.string.share_message));
    startActivity(chooser);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    presenter.onBackPressed();
  }
}

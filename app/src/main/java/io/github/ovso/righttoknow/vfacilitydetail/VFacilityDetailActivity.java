package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.main.BaseActivity;

/**
 * Created by jaeho on 2017. 8. 2
 */

public class VFacilityDetailActivity extends BaseActivity implements VFacilityDetailPresenter.View {

  VFacilityDetailPresenter presenter;
  @BindView(R.id.contents_textview) TextView contentsTextView;
  @BindView(R.id.progressbar) ProgressBar progressbar;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = new VFacilityDetailPresenterImpl(this);
    presenter.onCreate(savedInstanceState, getIntent());
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_vfacilitydetail;
  }

  @DebugLog @Override public void hideLoading() {
    new Handler().postDelayed(() -> progressbar.setVisibility(View.INVISIBLE), 500);
  }

  @DebugLog @Override public void showLoading() {
    //progressbar.setVisibility(View.VISIBLE);
  }

  @Override public void setSupportActionBar() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override public void showContents(String contents) {
    contentsTextView.setText(contents);
  }

  @Override public void setListener() {
    progressbar.getIndeterminateDrawable()
        .setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary),
            android.graphics.PorterDuff.Mode.MULTIPLY);
  }

  @BindView(R.id.content_view) View contentView;

  @Override public void showSnackbar(String msg) {
    Snackbar.make(contentView, msg, Snackbar.LENGTH_SHORT).show();
  }

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

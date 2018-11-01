package io.github.ovso.righttoknow.ui.violation_contents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.AdsActivity;
import io.github.ovso.righttoknow.ui.map.MapActivity;
import javax.inject.Inject;

public class ViolationContentsActivity extends AdsActivity implements ViolationContentsPresenter.View {

  @Inject ViolationContentsPresenter presenter;
  @BindView(R.id.contents_textview) TextView contentsTextView;
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipe;
  @BindView(R.id.share_button) Button shareButton;
  @BindView(R.id.location_button) Button locationButton;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter.onCreate(savedInstanceState, getIntent());
  }

  @Override public void setTitle(int titleId) {
    super.setTitle(titleId);
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
    swipe.setColorSchemeResources(R.color.colorPrimary);
    swipe.setOnRefreshListener(() -> presenter.onRefresh(ViolationContentsActivity.this.getIntent()));
  }

  @Override public void showLoading() {
    swipe.setRefreshing(true);
  }

  @Override public void hideLoading() {
    swipe.setRefreshing(false);
  }

  @Override public void showMessage(int resId) {
    new AlertDialog.Builder(this).setMessage(resId)
        .setPositiveButton(android.R.string.ok,
            (dialogInterface, which) -> dialogInterface.dismiss())
        .show();
  }

  @Override public void navigateToMap(double[] locations, String facName) {
    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
    intent.putExtra("locations", locations);
    intent.putExtra("facName", facName);
    startActivity(intent);
  }

  @Override public void hideButton() {
    locationButton.setVisibility(View.INVISIBLE);
    shareButton.setVisibility(View.INVISIBLE);
  }

  @Override public void showButton() {
    locationButton.setVisibility(View.VISIBLE);
    shareButton.setVisibility(View.VISIBLE);
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

  @OnClick(R.id.location_button) void onMapClick() {
    presenter.onMapClick();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override protected void onResume() {
    super.onResume();
    if (getIntent().hasExtra("vio_fac_link")) {
      setTitle(R.string.title_vioation_facility_inquiry_detail);
    } else if (getIntent().hasExtra("violator_link")) {
      setTitle(R.string.title_violator_inquiry_detail);
    }
  }
}

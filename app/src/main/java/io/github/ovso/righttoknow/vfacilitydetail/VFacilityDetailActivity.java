package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.main.BaseActivity;
import io.github.ovso.righttoknow.map.MapActivity;

/**
 * Created by jaeho on 2017. 8. 2
 */

public class VFacilityDetailActivity extends BaseActivity implements VFacilityDetailPresenter.View {

  VFacilityDetailPresenter presenter;
  @BindView(R.id.contents_textview) TextView contentsTextView;
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipe;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = new VFacilityDetailPresenterImpl(this);
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
    swipe.setOnRefreshListener(() -> {
      presenter.onRefresh(getIntent());
    });
  }

  @Override public void showAd() {
    CaulyAdView view;
    CaulyAdInfo info =
        new CaulyAdInfoBuilder(Security.CAULY_APP_CODE).effect(CaulyAdInfo.Effect.Circle.toString())
            .build();
    view = new CaulyAdView(this);
    view.setAdInfo(info);
    view.setAdViewListener(new CaulyAdViewListener() {
      @Override public void onReceiveAd(CaulyAdView caulyAdView, boolean b) {

      }

      @Override public void onFailedToReceiveAd(CaulyAdView caulyAdView, int i, String s) {

      }

      @Override public void onShowLandingScreen(CaulyAdView caulyAdView) {

      }

      @Override public void onCloseLandingScreen(CaulyAdView caulyAdView) {

      }
    });

    ViewGroup adContainer = findViewById(R.id.ad_container);
    adContainer.addView(view);
  }

  @Override public void showLoading() {
    swipe.setRefreshing(true);
  }

  @Override public void hideLoading() {
    swipe.setRefreshing(false);
  }

  @Override public void showMessage(int resId) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
  }

  @Override public void navigateToMap(String address) {
    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
    intent.putExtra("address", address);
    startActivity(intent);
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

  @OnClick(R.id.location_button) void onMapClick() {
    presenter.onMapClick(getIntent());
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    presenter.onBackPressed();
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

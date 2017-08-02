package io.github.ovso.righttoknow.vfacilitydetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.main.BaseActivity;
import io.github.ovso.righttoknow.vfacilitydetail.vo.VFacilityDetail;

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

  @Override public void hideLoading() {

  }

  @Override public void showLoading() {

  }

  @Override public void setSupportActionBar() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override public void showContents(VFacilityDetail vFacilityDetail) {
    StringBuilder builder = new StringBuilder();
    builder.append("시도 : ").append(vFacilityDetail.getSido()).append("\n");
    builder.append("시군구 : ").append(vFacilityDetail.getSigungu()).append("\n");
    builder.append("> 현재 <").append("\n");
    builder.append(vFacilityDetail.getName()).append("\n");;
    builder.append("대표자 : ").append(vFacilityDetail.getBoss()).append("\n");
    builder.append("원장 : ").append(vFacilityDetail.getDirector()).append("\n");
    builder.append("> 위반당시 <").append("\n");
    builder.append(vFacilityDetail.getOldName()).append("\n");;
    builder.append("대표자 : ").append(vFacilityDetail.getOldBoss()).append("\n");
    builder.append("원장 : ").append(vFacilityDetail.getOldDirector()).append("\n");
    builder.append("> 위반헹위 <").append("\n");
    builder.append(vFacilityDetail.getAction()).append("\n");
    builder.append("> 처분내용 <").append("\n");
    builder.append(vFacilityDetail.getDisposal());
    contentsTextView.setMovementMethod(new ScrollingMovementMethod());
    contentsTextView.setText(builder.toString());
  }

  @Override public void showNoContents() {

  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return true;
  }
}

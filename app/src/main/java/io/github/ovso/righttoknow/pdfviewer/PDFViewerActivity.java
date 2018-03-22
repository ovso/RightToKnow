package io.github.ovso.righttoknow.pdfviewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.ViewGroup;
import butterknife.BindView;
import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;
import com.github.barteksc.pdfviewer.PDFView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.main.BaseActivity;
import java.io.File;

/**
 * Created by jaeho on 2017. 8. 21
 */

public class PDFViewerActivity extends BaseActivity {
  @BindView(R.id.pdf_view) PDFView pdfView;
  @BindView(R.id.ad_container) ViewGroup adContainer;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(R.string.title_child_certified_detail);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    if (getIntent().hasExtra("file")) {
      File file = (File) getIntent().getSerializableExtra("file");
      pdfView.fromFile(file).load();
    }

    showAd();
  }

  private void showAd() {
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
    adContainer.addView(view);
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_pdfviewer;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return true;
  }
}
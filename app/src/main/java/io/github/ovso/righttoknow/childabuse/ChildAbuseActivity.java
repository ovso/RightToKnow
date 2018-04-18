package io.github.ovso.righttoknow.childabuse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.main.BaseActivity;

/**
 * Created by jaeho on 2018. 1. 4
 */

public class ChildAbuseActivity extends BaseActivity {
  @BindView(R.id.webview) WebView webView;

  @Override protected int getLayoutResId() {
    return R.layout.activity_child_abuse;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle(R.string.child_abuse);
    WebSettings settings = webView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setBuiltInZoomControls(true);
    settings.setDisplayZoomControls(true);
    settings.setLoadWithOverviewMode(true);
    settings.setUseWideViewPort(true);
    settings.setSupportZoom(true);
    webView.setWebViewClient(new WebViewClient());
    webView.setWebChromeClient(new WebChromeClient());
    webView.loadUrl(
        "http://m.post.naver.com/viewer/postView.nhn?volumeNo=9367173&memberNo=22718804&vType=VERTICAL");

    showAd();
  }

  private void showAd() {
    CaulyAdView view;
    CaulyAdInfo info =
        new CaulyAdInfoBuilder(Security.CAULY_APP_CODE.getValue()).effect(
            CaulyAdInfo.Effect.Circle.toString())
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

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return super.onOptionsItemSelected(item);
  }
}

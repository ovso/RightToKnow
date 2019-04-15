package io.github.ovso.righttoknow.ui.childabuse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.AdsActivity;

public class ChildAbuseActivity extends AdsActivity {
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
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return super.onOptionsItemSelected(item);
  }
}

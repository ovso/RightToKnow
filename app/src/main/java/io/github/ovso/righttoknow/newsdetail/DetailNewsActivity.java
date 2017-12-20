package io.github.ovso.righttoknow.newsdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.SpannableString;
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
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.main.BaseActivity;
import io.github.ovso.righttoknow.news.model.News;

/**
 * Created by jaeho on 2017. 9. 2
 */

public class DetailNewsActivity extends BaseActivity {
  @Override protected int getLayoutResId() {
    return R.layout.activity_detail_news;
  }

  @BindView(R.id.webview) WebView webView;
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;

  private News news;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    if (intent.hasExtra("news")) {
      setInit();
      setWebView();
      setSwipeRefresh();
      setTitle();
      loadUrl();
    }
    adView();
  }

  private void adView() {
    CaulyAdView view;
    CaulyAdInfo info = new CaulyAdInfoBuilder(Constants.CAULY_APP_CODE).effect(
        CaulyAdInfo.Effect.Circle.toString()).build();
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
    activityFinish();
    return super.onOptionsItemSelected(item);
  }

  private void activityFinish() {
    finish();
    webView.stopLoading();
  }

  private void setTitle() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(new SpannableString(Html.fromHtml(news.getTitle())));
  }

  private void loadUrl() {
    swipeRefresh.setRefreshing(true);
    webView.loadUrl(news.getLink());
  }

  private void setInit() {
    news = (News) getIntent().getSerializableExtra("news");
  }

  private void setSwipeRefresh() {
    swipeRefresh.setOnRefreshListener(() -> webView.loadUrl(news.getLink()));
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
  }

  private void setWebView() {
    WebSettings settings = webView.getSettings();
    settings.setJavaScriptEnabled(true);
    webView.setWebChromeClient(new WebChromeClient());
    webView.setWebViewClient(new MyWebViewClient());
    webView.setOnTouchListener((view, motionEvent) -> true);
  }

  class MyWebViewClient extends WebViewClient {
    @Override public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      if (swipeRefresh != null) {
        swipeRefresh.setRefreshing(false);
      }
    }
  }
}

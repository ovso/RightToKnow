package io.github.ovso.righttoknow.newsdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.main.BaseActivity;
import io.github.ovso.righttoknow.news.vo.News;

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
    getSupportActionBar().setTitle(news.getTitle());
  }
  private void loadUrl() {
    swipeRefresh.setRefreshing(true);
    webView.loadUrl(news.getUrl());
  }

  private void setInit() {
    news = (News) getIntent().getSerializableExtra("news");
  }

  private void setSwipeRefresh() {
    swipeRefresh.setOnRefreshListener(() -> webView.loadUrl(news.getUrl()));
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
  }

  private void setWebView() {
    webView.getSettings().setJavaScriptEnabled(true);
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

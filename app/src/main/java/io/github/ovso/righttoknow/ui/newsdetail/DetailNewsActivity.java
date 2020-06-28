package io.github.ovso.righttoknow.ui.newsdetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.AdsActivity;
import io.github.ovso.righttoknow.ui.main.news.model.News;

public class DetailNewsActivity extends AdsActivity {
  @BindView(R.id.webview)
  WebView webView;

  @BindView(R.id.swipe_refresh)
  SwipeRefreshLayout swipeRefresh;

  private News news;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_detail_news;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
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

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    activityFinish();
    return super.onOptionsItemSelected(item);
  }

  private void activityFinish() {
    finish();
    webView.stopLoading();
  }

  private void setTitle() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle(new SpannableString(Html.fromHtml(news.getTitle())));
    }
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

  @SuppressLint("SetJavaScriptEnabled")
  private void setWebView() {
    WebSettings settings = webView.getSettings();
    settings.setJavaScriptEnabled(true);
    webView.setWebChromeClient(new MyWebChromeClient(webClientListener));
    webView.setWebViewClient(new MyWebViewClient(webClientListener));
  }

  static class MyWebChromeClient extends WebChromeClient {

    private final WebClientListener l;

    public MyWebChromeClient(WebClientListener l) {
      this.l = l;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
      super.onProgressChanged(view, newProgress);
      if (l != null) l.onProgressChanged(newProgress);
    }
  }

  static class MyWebViewClient extends WebViewClient {

    private final WebClientListener l;

    MyWebViewClient(WebClientListener l) {
      this.l = l;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);
      if (l != null) l.onPageStarted();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      if (l != null) l.onPageFinish();
    }
  }

  private SimpleWebClientListener webClientListener =
      new SimpleWebClientListener() {
        @Override
        public void onPageStarted() {
          super.onPageStarted();
        }

        @Override
        public void onPageFinish() {
          super.onPageFinish();
        }

        @Override
        public void onProgressChanged(int newProgress) {
          super.onProgressChanged(newProgress);
        }
      };

  static class SimpleWebClientListener implements WebClientListener {

    @Override
    public void onPageStarted() {}

    @Override
    public void onPageFinish() {}

    @Override
    public void onProgressChanged(int newProgress) {}
  }

  interface WebClientListener {
    void onPageStarted();

    void onPageFinish();

    void onProgressChanged(int newProgress);
  }
}

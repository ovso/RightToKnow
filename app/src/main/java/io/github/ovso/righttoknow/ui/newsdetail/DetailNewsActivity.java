package io.github.ovso.righttoknow.ui.newsdetail;

import android.content.Intent;
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
    swipeRefresh.setOnRefreshListener(
        new SwipeRefreshLayout.OnRefreshListener() {
          @Override
          public void onRefresh() {
            webView.loadUrl(news.getLink());
          }
        });
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
  }

  private void setWebView() {
    WebSettings settings = webView.getSettings();
    settings.setJavaScriptEnabled(true);
    webView.setWebChromeClient(new WebChromeClient());
    webView.setWebViewClient(new MyWebViewClient());
  }

  class MyWebViewClient extends WebViewClient {
    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      if (swipeRefresh != null) {
        swipeRefresh.setRefreshing(false);
      }
    }
  }
}

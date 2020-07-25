package io.github.ovso.righttoknow.ui.newsdetail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.text.SpannableString
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.text.HtmlCompat
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.AdsActivity
import io.github.ovso.righttoknow.ui.main.news.model.News
import kotlinx.android.synthetic.main.app_bar_detail_news.*
import kotlinx.android.synthetic.main.content_news_detail.*

class DetailNewsActivity : AdsActivity() {

  private lateinit var news: News

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail_news)
    setSupportActionBar(toolbar)
    pb_new_detail.hide()
    showAds()
    val intent = intent
    if (intent.hasExtra("news")) {
      setInit()
      setWebView()
      setTitle()
      loadUrl()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    activityFinish()
    return super.onOptionsItemSelected(item)
  }

  private fun activityFinish() {
    finish()
    webview.stopLoading()
  }

  private fun setTitle() {
    supportActionBar?.run {
      setDisplayHomeAsUpEnabled(true)
      title = SpannableString(
        HtmlCompat.fromHtml(news.title, HtmlCompat.FROM_HTML_MODE_COMPACT)
      )
    }
  }

  private fun loadUrl() {
    webview.loadUrl(news.link)
  }

  private fun setInit() {
    news = intent.getSerializableExtra("news") as News
  }

  @SuppressLint("SetJavaScriptEnabled")
  private fun setWebView() {
    val settings = webview.settings
    settings.javaScriptEnabled = true
    webview.webChromeClient = MyWebChromeClient(webClientListener)
    webview.webViewClient = MyWebViewClient(webClientListener)
  }

  override fun onDestroy() {
    webview.destroy()
    super.onDestroy()
  }

  internal class MyWebChromeClient(private val l: WebClientListener?) : WebChromeClient() {
    override fun onProgressChanged(view: WebView, newProgress: Int) {
      super.onProgressChanged(view, newProgress)
      l?.onProgressChanged(newProgress)
    }

  }

  internal class MyWebViewClient(private val l: WebClientListener?) : WebViewClient() {
    override fun onPageStarted(
      view: WebView?,
      url: String?,
      favicon: Bitmap?
    ) {
      super.onPageStarted(view, url, favicon)
      l?.onPageStarted()
    }

    override fun onPageFinished(view: WebView, url: String) {
      super.onPageFinished(view, url)
      l?.onPageFinish()
    }

  }

  private val webClientListener: SimpleWebClientListener = object : SimpleWebClientListener() {
    override fun onPageStarted() {
      super.onPageStarted()
      pb_new_detail.show()
    }

    override fun onPageFinish() {
      super.onPageFinish()
      pb_new_detail.hide()
    }

    override fun onProgressChanged(newProgress: Int) {
      super.onProgressChanged(newProgress)
      pb_new_detail.progress = newProgress
    }
  }

  internal open class SimpleWebClientListener : WebClientListener {
    override fun onPageStarted() {}
    override fun onPageFinish() {}
    override fun onProgressChanged(newProgress: Int) {}
  }

  internal interface WebClientListener {
    fun onPageStarted()
    fun onPageFinish()
    fun onProgressChanged(newProgress: Int)
  }

  override fun onBackPressed() {
    if (!webview.canGoBack()) {
      super.onBackPressed()
    } else {
      webview.goBack()
    }
  }
}

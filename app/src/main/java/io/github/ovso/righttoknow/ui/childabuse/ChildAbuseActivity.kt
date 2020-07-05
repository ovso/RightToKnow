package io.github.ovso.righttoknow.ui.childabuse

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.databinding.ActivityChildAbuseBinding
import io.github.ovso.righttoknow.exts.viewBinding
import io.github.ovso.righttoknow.framework.AdsActivity
import kotlinx.android.synthetic.main.content_abuse.*

class ChildAbuseActivity : AdsActivity() {

  private val binding by viewBinding(ActivityChildAbuseBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    setTitle(R.string.child_abuse)
    val settings = webview.settings
    settings.javaScriptEnabled = true
    settings.builtInZoomControls = true
    settings.displayZoomControls = true
    settings.loadWithOverviewMode = true
    settings.useWideViewPort = true
    settings.setSupportZoom(true)
    webview.webViewClient = WebViewClient()
    webview.webChromeClient = WebChromeClient()
    webview.loadUrl(
      "http://m.post.naver.com/viewer/postView.nhn?volumeNo=9367173&memberNo=22718804&vType=VERTICAL")
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    finish()
    return super.onOptionsItemSelected(item)
  }
}
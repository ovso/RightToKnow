package io.github.ovso.righttoknow.framework

import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import dagger.android.support.DaggerAppCompatActivity
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.ad.MyAdView

abstract class BaseActivity : DaggerAppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    toolbar?.let { setSupportActionBar(it) }
    setNavigationBarColor()
    showAd()
  }

  protected fun showAd() {
    val adContainer = findViewById<ViewGroup>(R.id.ad_container)
    adContainer?.addView(MyAdView.getAdmobAdView(applicationContext))
  }

  private fun setNavigationBarColor() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
    }
  }

  protected val toolbar: Toolbar
    protected get() = findViewById(R.id.toolbar)
}
package io.github.ovso.righttoknow

import android.content.Context
import com.downloader.PRDownloader
import com.google.android.gms.ads.MobileAds
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import io.github.ovso.righttoknow.framework.utils.SystemUtils
import timber.log.Timber
import timber.log.Timber.DebugTree

object Library {

  fun init(context: Context) {
    logger(context)
    adMob(context)
    timber(context)
    pdfDownloader(context)
  }

  private fun pdfDownloader(context: Context) {
    PRDownloader.initialize(context)
  }

  private fun timber(context: Context) {
    if (SystemUtils.isDebuggable(context)) {
      Timber.plant(DebugTree())
    }
  }

  private fun adMob(context: Context) {
    MobileAds.initialize(context) { }
  }

  private fun logger(context: Context) {
    Logger.addLogAdapter(object : AndroidLogAdapter() {
      override fun isLoggable(priority: Int, tag: String?): Boolean = SystemUtils.isDebuggable(context)
    })
  }

}

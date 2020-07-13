package io.github.ovso.righttoknow

import android.app.Application
import io.github.ovso.righttoknow.framework.utils.AppInitUtils
import io.github.ovso.righttoknow.framework.utils.MessagingHandler

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    instance = this
    DEBUG = AppInitUtils.debug(applicationContext)
    AppInitUtils.ad(applicationContext)
    AppInitUtils.timber(DEBUG)
    AppInitUtils.prDownloader(applicationContext)
    MessagingHandler.createChannelToShowNotifications()
  }

  companion object {
    var DEBUG = false
    @JvmStatic
    lateinit var instance: App
      private set
  }
}
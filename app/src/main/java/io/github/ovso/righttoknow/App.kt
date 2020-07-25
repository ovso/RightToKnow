package io.github.ovso.righttoknow

import android.app.Application
import io.github.ovso.righttoknow.framework.utils.SystemUtils
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import java.io.IOException
import java.net.SocketException

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    instance = this
    DEBUG = SystemUtils.isDebuggable(this)
    Library.init(this)
    setupRxJavaHandling()
  }

  private fun setupRxJavaHandling() {
    RxJavaPlugins.setErrorHandler { e ->
      var error = e
      if (error is UndeliverableException) {
        error = e.cause
      }
      if (error is IOException || error is SocketException) {
        // fine, irrelevant network problem or API that throws on cancellation
        return@setErrorHandler
      }
      if (error is InterruptedException) {
        // fine, some blocking code was interrupted by a dispose call
        return@setErrorHandler
      }
      if (error is NullPointerException || error is IllegalArgumentException) {
        // that's likely a bug in the application
        Thread.currentThread().uncaughtExceptionHandler
          .uncaughtException(Thread.currentThread(), error)
        return@setErrorHandler
      }
      if (error is IllegalStateException) {
        // that's a bug in RxJava or in a custom operator
        Thread.currentThread().uncaughtExceptionHandler
          .uncaughtException(Thread.currentThread(), error)
        return@setErrorHandler
      }
      Timber.e(e)
    }
  }

  companion object {
    var DEBUG = false

    @JvmStatic
    lateinit var instance: App
      private set
  }
}

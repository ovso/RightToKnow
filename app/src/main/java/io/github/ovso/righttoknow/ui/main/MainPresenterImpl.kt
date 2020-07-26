package io.github.ovso.righttoknow.ui.main

import android.content.Intent
import com.orhanobut.logger.Logger
import io.github.ovso.righttoknow.App.Companion.instance
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.data.network.Repository
import io.github.ovso.righttoknow.data.toObject
import io.github.ovso.righttoknow.exts.plusAssign
import io.github.ovso.righttoknow.framework.utils.Utility
import io.github.ovso.righttoknow.utils.ResourceProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

typealias Log = Logger

class MainPresenterImpl internal constructor(
  private val view: MainPresenter.View,
  private val resourceProvider: ResourceProvider,
  private val repository: Repository,
  private val compositeDisposable: CompositeDisposable
) : MainPresenter {

  override fun onBackPressed(isDrawerOpen: Boolean) {
    if (isDrawerOpen) {
      view.closeDrawer()
    } else {
      view.finish()
    }
  }

  override fun onCreate(intent: Intent) {
    val versionName =
      resourceProvider.getString(R.string.version) + " " + Utility.getVersionName(
        instance
      )
    view.setVersionName(versionName)
    view.setListener()
    view.setSearchView()
    view.showViolationFragment()
    reqAdsData()
  }

  private fun reqAdsData() {
    repository.fetchAdsData().addOnCompleteListener { task ->
      if (task.isSuccessful) {
        val updated = task.result
        Log.d("updated = $updated")
        compositeDisposable += repository.getAdsValue("ad_type").toObject<AdsData>()
          .subscribe({
            when (it.type) {
              0 -> view.showBanner() // admob
              1 -> { // admob + other
                view.showBanner()
                view.showOtherBanner(it.imgUrl, it.navUrl)
              }
              2 -> view.showOtherBanner(it.imgUrl, it.navUrl) // other
            }
          }, Logger::d)
      }
    }
  }

  override fun onNavigationItemSelected(id: Int) {
    when (id) {
      R.id.nav_child_abuse -> view.navigateToChildAbuse()
      R.id.nav_opensource -> view.navigateToOssLicensesMenu()
      R.id.nav_help -> {
        val msg = resourceProvider.getString(R.string.help_content)
        view.showHelpAlert(msg)
      }
      R.id.nav_share -> view.navigateToShare()
    }
  }

  override fun onBottomNavigationItemSelected(id: Int): Boolean {
    when (id) {
      R.id.bottom_nav_violation_facility -> view.showViolationFragment()
      R.id.bottom_nav_violator -> view.showViolatorFragment()
      R.id.bottom_nav_certified -> view.showCertifiedFragment()
      R.id.bottom_nav_news -> view.showNewsFragment()
      R.id.bottom_nav_video -> view.showVideoFragment()
    }
    return true
  }

  init {
    view.changeTheme()
  }
}

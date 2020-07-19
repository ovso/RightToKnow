package io.github.ovso.righttoknow.ui.main

import android.content.Intent
import io.github.ovso.righttoknow.App.Companion.instance
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.utils.Constants
import io.github.ovso.righttoknow.framework.utils.Utility
import io.github.ovso.righttoknow.utils.ResourceProvider

class MainPresenterImpl internal constructor(
  private val view: MainPresenter.View,
  private val resourceProvider: ResourceProvider
) : MainPresenter {
  override fun onNewIntent(intent: Intent) {
    fcmNav(intent)
  }

  override fun onBackPressed(isDrawerOpen: Boolean) {
    if (isDrawerOpen) {
      view.closeDrawer()
    } else {
      view.finish()
    }
  }

  private fun fcmNav(intent: Intent) {
    val position = intent.getIntExtra(
      Constants.FCM_KEY_CONTENT_POSITION,
      0
    )
    when (position) {
      Constants.ITEM_VIOLATION_FACILITY -> view.showViolationFragment()
      Constants.ITEM_VIOLATOR -> view.showViolatorFragment()
      Constants.ITEM_CERTIFIED -> view.showCertifiedFragment()
      Constants.ITEM_NEWS -> view.showNewsFragment()
      Constants.ITEM_VIDEO -> view.showVideoFragment()
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
    fcmNav(intent)
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
package io.github.ovso.righttoknow.ui.main

import android.content.Intent
import androidx.annotation.StringRes

interface MainPresenter {
  fun onCreate(intent: Intent)
  fun onNavigationItemSelected(id: Int)
  fun onBottomNavigationItemSelected(id: Int): Boolean
  fun onNewIntent(intent: Intent)
  fun onBackPressed(isDrawerOpen: Boolean)
  interface View {
    fun setListener()
    fun setBottomNavigationViewBehavior()
    fun setCheckedBottomNavigationView(position: Int)
    fun setVersionName(versionName: String)
    fun changeTheme()
    fun closeSearchView()
    fun setSearchView()
    fun navigateToSettings()
    fun closeDrawer()
    fun finish()
    fun showHelpAlert(msg: String)
    fun navigateToChildAbuse()
    fun showViolationFragment()
    fun showViolatorFragment()
    fun showCertifiedFragment()
    fun showNewsFragment()
    fun showVideoFragment()
    fun showMessage(@StringRes resId: Int)
    fun hideSearchView()
    fun showSearchView()
    fun navigateToShare()
    fun navigateToOssLicensesMenu()
  }
}
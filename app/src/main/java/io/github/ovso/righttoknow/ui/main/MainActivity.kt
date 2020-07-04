package io.github.ovso.righttoknow.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.databinding.ActivityMainBinding
import io.github.ovso.righttoknow.exts.viewBinding
import io.github.ovso.righttoknow.framework.BaseActivity
import io.github.ovso.righttoknow.framework.customview.BottomNavigationViewBehavior
import io.github.ovso.righttoknow.ui.childabuse.ChildAbuseActivity
import io.github.ovso.righttoknow.ui.main.certified.CertifiedFragment
import io.github.ovso.righttoknow.ui.main.news.NewsFragment
import io.github.ovso.righttoknow.ui.main.video.VideoFragment
import io.github.ovso.righttoknow.ui.main.violationfacility.ViolationFacilityFragment
import io.github.ovso.righttoknow.ui.main.violator.ViolatorFragment
import io.github.ovso.righttoknow.ui.settings.SettingsActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainPresenter.View {
  @Inject
  lateinit var presenter: MainPresenter

  private val binding by viewBinding(ActivityMainBinding::inflate)

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    presenter.onCreate(intent)
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    presenter.onNewIntent(intent)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun setListener() {
    val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open,
      R.string.navigation_drawer_close)
    binding.drawerLayout.addDrawerListener(toggle)
    toggle.syncState()
    binding.navView.setNavigationItemSelectedListener { item: MenuItem ->
      presenter.onNavigationItemSelected(item.itemId)
      binding.drawerLayout.closeDrawer(GravityCompat.START)
      true
    }
    with(binding.includeContentContainer.includeMainContainer.bottomNavigationView) {
      setOnNavigationItemSelectedListener { item: MenuItem -> presenter.onBottomNavigationItemSelected(item.itemId) }
    }
  }

  override fun setBottomNavigationViewBehavior() {
    try {
      val bnv = binding.includeContentContainer.includeMainContainer.bottomNavigationView
      val layoutParams = bnv.layoutParams as CoordinatorLayout.LayoutParams
      layoutParams.behavior = BottomNavigationViewBehavior()
    } catch (e: ClassCastException) {
      e.printStackTrace()
    }
  }

  override fun setCheckedBottomNavigationView(position: Int) {
    binding.includeContentContainer.includeMainContainer.bottomNavigationView.menu.getItem(position).isChecked = true
  }

  override fun setVersionName(versionName: String) {
    val view = binding.navView.getHeaderView(0)
    val versionNameView = view.findViewById<TextView>(R.id.version_name_textview)
    versionNameView.text = versionName
  }

  override fun changeTheme() {
    setTheme(R.style.AppTheme_NoActionBar)
  }

  override fun closeSearchView() {}
  override fun setSearchView() {}
  override fun navigateToSettings() {
    val intent = Intent(this, SettingsActivity::class.java)
    startActivity(intent)
  }

  override fun navigateToChildAbuse() {
    val intent = Intent(this, ChildAbuseActivity::class.java)
    startActivity(intent)
  }

  override fun showViolationFragment() {
    supportFragmentManager.beginTransaction()
      .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
        R.animator.enter_animation, R.animator.exit_animation)
      .replace(R.id.fragment_container, ViolationFacilityFragment.newInstance())
      .commit()
  }

  override fun showViolatorFragment() {
    supportFragmentManager.beginTransaction()
      .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
        R.animator.enter_animation, R.animator.exit_animation)
      .replace(R.id.fragment_container, ViolatorFragment.newInstance())
      .commit()
  }

  override fun showCertifiedFragment() {
    supportFragmentManager.beginTransaction()
      .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
        R.animator.enter_animation, R.animator.exit_animation)
      .replace(R.id.fragment_container, CertifiedFragment.newInstance())
      .commit()
  }

  override fun showNewsFragment() {
    supportFragmentManager.beginTransaction()
      .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
        R.animator.enter_animation, R.animator.exit_animation)
      .replace(R.id.fragment_container, NewsFragment.newInstance())
      .commit()
  }

  override fun showVideoFragment() {
    supportFragmentManager.beginTransaction()
      .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
        R.animator.enter_animation, R.animator.exit_animation)
      .replace(R.id.fragment_container, VideoFragment.newInstance())
      .commit()
  }

  override fun showMessage(resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
  }

  override fun hideSearchView() {}
  override fun showSearchView() {}
  override fun navigateToShare() {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    val subject = getString(R.string.app_name)
    val text = "https://play.google.com/store/apps/details?id=io.github.ovso.righttoknow"
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, text)
    val chooser = Intent.createChooser(intent, getString(R.string.share_message))
    startActivity(chooser)
  }

  override fun onBackPressed() {
    presenter.onBackPressed(binding.drawerLayout.isDrawerOpen(GravityCompat.START))
  }

  override fun closeDrawer() {
    binding.drawerLayout.closeDrawer(GravityCompat.START)
  }

  override fun showHelpAlert(msg: String) {
    AlertDialog.Builder(this).setTitle(R.string.help).setMessage(msg).show()
  }
}
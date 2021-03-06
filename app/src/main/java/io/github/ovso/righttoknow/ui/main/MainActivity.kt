package io.github.ovso.righttoknow.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.firebase.ktx.Firebase
import com.google.gson.annotations.SerializedName
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.data.network.Repository
import io.github.ovso.righttoknow.databinding.ActivityMainBinding
import io.github.ovso.righttoknow.exts.launchActivity
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
import io.github.ovso.righttoknow.utils.ResourceProvider
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_main_other_ads_container.*


class MainActivity : BaseActivity(), MainPresenter.View {
  private lateinit var presenter: MainPresenter
  private val binding by viewBinding(ActivityMainBinding::inflate)
  private val compositeDisposable by lazy {
    CompositeDisposable()
  }

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setSupportActionBar(toolbar)
    presenter = MainPresenterImpl(
      this,
      ResourceProvider(this),
      Repository(Firebase),
      CompositeDisposable()
    )
    presenter.onCreate(intent)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun setListener() {
    val toggle = ActionBarDrawerToggle(
      this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open,
      R.string.navigation_drawer_close
    )
    binding.drawerLayout.addDrawerListener(toggle)
    toggle.syncState()
    binding.navView.setNavigationItemSelectedListener { item: MenuItem ->
      presenter.onNavigationItemSelected(item.itemId)
      binding.drawerLayout.closeDrawer(GravityCompat.START)
      true
    }
    with(binding.includeContentContainer.includeMainContainer.bottomNavigationView) {
      setOnNavigationItemSelectedListener { item: MenuItem ->
        presenter.onBottomNavigationItemSelected(
          item.itemId
        )
      }
      setOnNavigationItemReselectedListener {

      }
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

  override fun navigateToOssLicensesMenu() {
    launchActivity<OssLicensesMenuActivity> { }
  }

  override fun showBanner() {
    showAds()
  }

  override fun showOtherBanner(imgUrl: String, navUrl: String) {
    Glide.with(ivMainOtherAds).load(imgUrl).into(ivMainOtherAds)
    ivMainOtherAds.updateLayoutParams<LinearLayout.LayoutParams> {
      height = resources.getDimensionPixelSize(R.dimen.main_other_ads_height)
    }
    ivMainOtherAds.setOnClickListener {
      navigateExternalBrowser(navUrl)
    }
  }

  private fun navigateExternalBrowser(navUrl: String) {
    startActivity(
      Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(navUrl)
      }
    )
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
      .setCustomAnimations(
        R.animator.enter_animation, R.animator.exit_animation,
        R.animator.enter_animation, R.animator.exit_animation
      )
      .replace(R.id.fragment_container, ViolationFacilityFragment.newInstance())
      .commit()
  }

  override fun showViolatorFragment() {
    supportFragmentManager.beginTransaction()
      .setCustomAnimations(
        R.animator.enter_animation, R.animator.exit_animation,
        R.animator.enter_animation, R.animator.exit_animation
      )
      .replace(R.id.fragment_container, ViolatorFragment.newInstance())
      .commit()
  }

  override fun showCertifiedFragment() {
    supportFragmentManager.beginTransaction()
      .setCustomAnimations(
        R.animator.enter_animation, R.animator.exit_animation,
        R.animator.enter_animation, R.animator.exit_animation
      )
      .replace(R.id.fragment_container, CertifiedFragment.newInstance())
      .commit()
  }

  override fun showNewsFragment() {
    supportFragmentManager.beginTransaction()
      .setCustomAnimations(
        R.animator.enter_animation, R.animator.exit_animation,
        R.animator.enter_animation, R.animator.exit_animation
      )
      .replace(R.id.fragment_container, NewsFragment.newInstance())
      .commit()
  }

  override fun showVideoFragment() {
    supportFragmentManager.beginTransaction()
      .setCustomAnimations(
        R.animator.enter_animation, R.animator.exit_animation,
        R.animator.enter_animation, R.animator.exit_animation
      )
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

  override fun onDestroy() {
    super.onDestroy()
    compositeDisposable.clear()
  }
}

data class AdsData(
  @SerializedName("type")
  val type: Int,
  @SerializedName("img_url")
  val imgUrl: String,
  @SerializedName("nav_url")
  val navUrl: String
)

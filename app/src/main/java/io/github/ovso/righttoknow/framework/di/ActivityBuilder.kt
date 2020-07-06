package io.github.ovso.righttoknow.framework.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.github.ovso.righttoknow.ui.childabuse.ChildAbuseActivity
import io.github.ovso.righttoknow.ui.childabuse.ChildAbuseActivityModule
import io.github.ovso.righttoknow.ui.main.MainActivity
import io.github.ovso.righttoknow.ui.main.MainActivityModule
import io.github.ovso.righttoknow.ui.map.MapActivity
import io.github.ovso.righttoknow.ui.map.MapActivityActivityModule
import io.github.ovso.righttoknow.ui.newsdetail.DetailNewsActivity
import io.github.ovso.righttoknow.ui.newsdetail.DetailNewsActivityModule
import io.github.ovso.righttoknow.ui.pdfviewer.PDFViewerActivity
import io.github.ovso.righttoknow.ui.pdfviewer.PDFViewerActivityModule
import io.github.ovso.righttoknow.ui.settings.SettingsActivity
import io.github.ovso.righttoknow.ui.settings.SettingsActivityModule
import io.github.ovso.righttoknow.ui.vfacilitydetail.VFacilityDetailActivity
import io.github.ovso.righttoknow.ui.vfacilitydetail.VFacilityDetailActivityModule
import io.github.ovso.righttoknow.ui.video.LandscapeVideoActivity
import io.github.ovso.righttoknow.ui.video.PortraitVideoActivity
import javax.inject.Singleton

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class ActivityBuilder {
  @Singleton
  @ContributesAndroidInjector(modules = [MainActivityModule::class])
  abstract fun bindMainActivity(): MainActivity?

  @Singleton
  @ContributesAndroidInjector(modules = [VFacilityDetailActivityModule::class])
  abstract fun bindVFacilityDetailActivity(): VFacilityDetailActivity?

  @Singleton
  @ContributesAndroidInjector(modules = [MapActivityActivityModule::class])
  abstract fun bindMapActivity(): MapActivity?

  @Singleton
  @ContributesAndroidInjector(modules = [PDFViewerActivityModule::class])
  abstract fun bindPDFViewerActivity(): PDFViewerActivity?

  @Singleton
  @ContributesAndroidInjector(modules = [DetailNewsActivityModule::class])
  abstract fun bindDetailNewsActivity(): DetailNewsActivity?

  @Singleton
  @ContributesAndroidInjector(modules = [SettingsActivityModule::class])
  abstract fun bindSettingsActivity(): SettingsActivity?

  @Singleton
  @ContributesAndroidInjector(modules = [ChildAbuseActivityModule::class])
  abstract fun bindChildAbuseActivity(): ChildAbuseActivity?

  @Singleton
  @ContributesAndroidInjector(modules = [])
  abstract fun bindLandscapeVideoActivity(): LandscapeVideoActivity?

  @Singleton
  @ContributesAndroidInjector(modules = [])
  abstract fun bindPortraitVideoActivity(): PortraitVideoActivity?
}
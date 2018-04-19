package io.github.ovso.righttoknow.framework.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import io.github.ovso.righttoknow.childabuse.ChildAbuseActivity;
import io.github.ovso.righttoknow.childabuse.ChildAbuseActivityModule;
import io.github.ovso.righttoknow.main.MainActivity;
import io.github.ovso.righttoknow.main.MainActivityModule;
import io.github.ovso.righttoknow.map.MapActivity;
import io.github.ovso.righttoknow.map.MapActivityActivityModule;
import io.github.ovso.righttoknow.newsdetail.DetailNewsActivity;
import io.github.ovso.righttoknow.newsdetail.DetailNewsActivityModule;
import io.github.ovso.righttoknow.pdfviewer.PDFViewerActivity;
import io.github.ovso.righttoknow.pdfviewer.PDFViewerActivityModule;
import io.github.ovso.righttoknow.settings.SettingsActivity;
import io.github.ovso.righttoknow.settings.SettingsActivityModule;
import io.github.ovso.righttoknow.vfacilitydetail.VFacilityDetailActivity;
import io.github.ovso.righttoknow.vfacilitydetail.VFacilityDetailActivityModule;
import javax.inject.Singleton;

@Module(includes = { AndroidSupportInjectionModule.class })
public abstract class ActivityBuilder {
  @Singleton @ContributesAndroidInjector(modules = { MainActivityModule.class })
  abstract MainActivity bindMainActivity();

  @Singleton @ContributesAndroidInjector(modules = { VFacilityDetailActivityModule.class })
  abstract VFacilityDetailActivity bindVFacilityDetailActivity();

  @Singleton @ContributesAndroidInjector(modules = { MapActivityActivityModule.class })
  abstract MapActivity bindMapActivity();

  @Singleton @ContributesAndroidInjector(modules = { PDFViewerActivityModule.class })
  abstract PDFViewerActivity bindPDFViewerActivity();

  @Singleton @ContributesAndroidInjector(modules = { DetailNewsActivityModule.class })
  abstract DetailNewsActivity bindDetailNewsActivity();

  @Singleton @ContributesAndroidInjector(modules = { SettingsActivityModule.class })
  abstract SettingsActivity bindSettingsActivity();

  @Singleton @ContributesAndroidInjector(modules = { ChildAbuseActivityModule.class })
  abstract ChildAbuseActivity bindChildAbuseActivity();
}
package io.github.ovso.righttoknow.framework.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import io.github.ovso.righttoknow.ui.childabuse.ChildAbuseActivity;
import io.github.ovso.righttoknow.ui.childabuse.ChildAbuseActivityModule;
import io.github.ovso.righttoknow.ui.main.MainActivity;
import io.github.ovso.righttoknow.ui.main.MainActivityModule;
import io.github.ovso.righttoknow.ui.map.MapActivity;
import io.github.ovso.righttoknow.ui.map.MapActivityActivityModule;
import io.github.ovso.righttoknow.ui.newsdetail.DetailNewsActivity;
import io.github.ovso.righttoknow.ui.newsdetail.DetailNewsActivityModule;
import io.github.ovso.righttoknow.ui.pdfviewer.PDFViewerActivity;
import io.github.ovso.righttoknow.ui.pdfviewer.PDFViewerActivityModule;
import io.github.ovso.righttoknow.ui.settings.SettingsActivity;
import io.github.ovso.righttoknow.ui.settings.SettingsActivityModule;
import io.github.ovso.righttoknow.ui.violation_contents.ViolationContentsActivity;
import io.github.ovso.righttoknow.ui.violation_contents.ViolationContentsActivityModule;
import io.github.ovso.righttoknow.ui.video.LandscapeVideoActivity;
import io.github.ovso.righttoknow.ui.video.PortraitVideoActivity;
import javax.inject.Singleton;

@Module(includes = { AndroidSupportInjectionModule.class })
public abstract class ActivityBuilder {
  @Singleton @ContributesAndroidInjector(modules = { MainActivityModule.class })
  abstract MainActivity bindMainActivity();

  @Singleton @ContributesAndroidInjector(modules = { ViolationContentsActivityModule.class })
  abstract ViolationContentsActivity bindVFacilityDetailActivity();

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

  @Singleton @ContributesAndroidInjector(modules = {})
  abstract LandscapeVideoActivity bindLandscapeVideoActivity();

  @Singleton @ContributesAndroidInjector(modules = {})
  abstract PortraitVideoActivity bindPortraitVideoActivity();
}
package io.github.ovso.righttoknow.framework.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import io.github.ovso.righttoknow.App

@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent : AndroidInjector<DaggerApplication?> {
  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application?): Builder
    fun build(): AppComponent
  }

  fun inject(app: App)
}
package io.github.ovso.righttoknow.data

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SchedulerProvider {
  fun io(): Scheduler = Schedulers.io()
  fun ui(): Scheduler = AndroidSchedulers.mainThread()
}

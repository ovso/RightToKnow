package io.github.ovso.righttoknow.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SchedulersFacade {
  fun io(): Scheduler = Schedulers.io()
  fun ui(): Scheduler = AndroidSchedulers.mainThread()
}
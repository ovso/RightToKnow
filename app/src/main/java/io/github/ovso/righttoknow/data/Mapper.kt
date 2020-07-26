package io.github.ovso.righttoknow.data

import com.google.gson.Gson
import io.reactivex.Single

inline fun <reified T> String.toObject(): Single<T> {
  return Single.fromCallable { Gson().fromJson(this, T::class.java) }
    .subscribeOn(SchedulerProvider.io())
}

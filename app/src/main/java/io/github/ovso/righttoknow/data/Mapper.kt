package io.github.ovso.righttoknow.data

import com.google.gson.Gson
import io.github.ovso.righttoknow.ui.main.AdsData
import io.reactivex.Single

//fun Gson.

fun aa() {
  Gson().fromJson<AdsData>("", AdsData::class.java)
}

inline fun <reified T> String.toObject(): Single<T> {
  return Single.fromCallable { Gson().fromJson(this, T::class.java) }
    .subscribeOn(SchedulerProvider.io())
}

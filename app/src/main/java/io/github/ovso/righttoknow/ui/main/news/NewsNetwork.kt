package io.github.ovso.righttoknow.ui.main.news

import android.content.Context
import androidx.annotation.StringRes
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.network.NetworkHelper
import io.github.ovso.righttoknow.ui.main.news.model.NewsResult
import io.reactivex.Single

class NewsNetwork(context: Context, baseUrl: String) : NetworkHelper(context, baseUrl) {
  fun getNews(@StringRes resId: Int): Single<NewsResult> {
    val query = context.getString(resId)
    val display = 50
    val start = 1
    val sort = context.getString(R.string.api_sort)
    return myApi.getNews(query, display, start, sort)
  }
}
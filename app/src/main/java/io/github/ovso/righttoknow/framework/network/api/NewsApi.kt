package io.github.ovso.righttoknow.framework.network.api

import io.github.ovso.righttoknow.ui.main.news.model.NewsResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.*

interface NewsApi {
  @GET("/v1/search/news")
  fun getNews(@QueryMap queryMap: HashMap<String, String>): Single<NewsResult>

  @GET("/v1/search/news")
  fun getNews(
    @Query("query") query: String,
    @Query("display") display: Int,
    @Query("start") start: Int,
    @Query("sort") sort: String): Single<NewsResult>
}
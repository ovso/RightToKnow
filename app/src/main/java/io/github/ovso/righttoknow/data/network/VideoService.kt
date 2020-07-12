package io.github.ovso.righttoknow.data.network

import io.github.ovso.righttoknow.data.network.model.video.Search
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface VideoService {
  @GET("youtube/v3/search")
  fun getResult(@QueryMap queryMap: Map<String, Any>): Single<Search>
}

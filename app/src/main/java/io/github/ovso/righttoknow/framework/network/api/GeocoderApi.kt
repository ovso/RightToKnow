package io.github.ovso.righttoknow.framework.network.api

import io.github.ovso.righttoknow.framework.network.model.GoogleGeocode
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.*

interface GeocoderApi {
  @GET("/maps/api/geocode/json")
  fun getGeocode(
    @QueryMap queryMap: HashMap<String, String>): Single<GoogleGeocode>
}
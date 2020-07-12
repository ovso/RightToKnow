package io.github.ovso.righttoknow.framework.network

import android.content.Context
import io.github.ovso.righttoknow.Security
import io.github.ovso.righttoknow.framework.network.api.GeocoderApi
import io.github.ovso.righttoknow.framework.network.model.GoogleGeocode
import io.reactivex.Single
import okhttp3.Request
import java.util.*

class GeocodeNetwork(
  context: Context,
  baseUrl: String
) : NetworkHelper2<GeocoderApi>(
  context,
  baseUrl
) {

  fun getGoogleGeocode(address: String): Single<GoogleGeocode> {
    val queryMap = HashMap<String, String>()
    queryMap["address"] = address
    queryMap["language"] = "ko"
    queryMap["key"] = Security.GOOGLE_API_KEY.value
    return api.getGeocode(queryMap)
  }

  companion object {
    const val GEOCODING_BASE_URL = "https://maps.googleapis.com"
  }

  override val apiClass: Class<GeocoderApi>
    get() = GeocoderApi::class.java

  override fun createRequst(original: Request): Request {
    return original.newBuilder().header("Content-Type", "plain/text").build()
  }
}

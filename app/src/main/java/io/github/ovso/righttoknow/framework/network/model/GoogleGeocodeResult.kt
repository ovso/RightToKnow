package io.github.ovso.righttoknow.framework.network.model

import com.google.gson.annotations.SerializedName

class GoogleGeocodeResult(
  @SerializedName("formatted_address")
  val address: String,
  val geometry: Geometry
)
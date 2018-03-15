package io.github.ovso.righttoknow.network.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * Created by jaeho on 2018. 3. 15..
 */

@Getter public class GoogleGeocodeResult {
  @SerializedName("formatted_address") private String address;
  private Geometry geometry;
}

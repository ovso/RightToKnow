package io.github.ovso.righttoknow.framework.network.api;

import io.github.ovso.righttoknow.framework.network.model.GoogleGeocode;
import io.reactivex.Single;
import java.util.HashMap;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by jaeho on 2018. 3. 12
 */

public interface GeocoderApi {
  @GET("/maps/api/geocode/json") Single<GoogleGeocode> getGeocode(
      @QueryMap HashMap<String, String> queryMap);
}

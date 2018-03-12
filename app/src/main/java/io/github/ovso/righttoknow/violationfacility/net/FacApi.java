package io.github.ovso.righttoknow.violationfacility.net;

import io.reactivex.Single;
import java.util.HashMap;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by jaeho on 2018. 3. 12
 */

public interface FacApi {
  @GET("/info/cfvp/VioltfcltySlL.jsp") Single<Object> getFacs(
      @QueryMap HashMap<String, String> queryMap);
}

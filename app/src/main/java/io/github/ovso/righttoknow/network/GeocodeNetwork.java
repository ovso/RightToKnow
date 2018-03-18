package io.github.ovso.righttoknow.network;

import android.content.Context;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.network.api.GeocoderApi;
import io.github.ovso.righttoknow.network.model.GoogleGeocode;
import io.reactivex.Single;
import java.util.HashMap;
import okhttp3.Request;

/**
 * Created by jaeho on 2018. 3. 12
 */

public class GeocodeNetwork extends NetworkHelper2<GeocoderApi> {
  public final static String GEOCODING_BASE_URL = "https://maps.googleapis.com";
  public GeocodeNetwork(Context context, String baseUrl) {
    super(context, baseUrl);
  }

  @Override protected Class<GeocoderApi> getApiClass() {
    return GeocoderApi.class;
  }

  @Override protected Request createRequst(Request original) {
    return original.newBuilder().header("Content-Type", "plain/text").build();
  }

  public Single<GoogleGeocode> getGoogleGeocode(String address) {
    HashMap<String, String> queryMap = new HashMap<>();
    queryMap.put("address", address);
    queryMap.put("language", "ko");
    queryMap.put("key", Security.GEOCODING_API_KEY);
    return getApi().getGeocode(queryMap);
  }
}

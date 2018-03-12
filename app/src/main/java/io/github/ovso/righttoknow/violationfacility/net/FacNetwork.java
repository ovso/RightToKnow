package io.github.ovso.righttoknow.violationfacility.net;

import android.content.Context;
import io.github.ovso.righttoknow.network.NetworkHelper2;
import io.reactivex.Single;
import java.util.HashMap;
import okhttp3.Request;

/**
 * Created by jaeho on 2018. 3. 12
 */

public class FacNetwork extends NetworkHelper2<FacApi> {
  public FacNetwork(Context context, String baseUrl) {
    super(context, baseUrl);
  }

  @Override protected Class<FacApi> getApiClass() {
    return FacApi.class;
  }

  @Override protected Request createRequst(Request original) {
    return original.newBuilder().header("Content-Type", "plain/text").build();
  }

  public Single<Object> getFacs() {
    HashMap<String, String> queryMap = new HashMap<>();
    queryMap.put("limit", "100");
    return getApi().getFacs(queryMap);
  }
}

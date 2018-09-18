package io.github.ovso.righttoknow.data.network;

import io.github.ovso.righttoknow.data.network.model.video.Search;
import io.reactivex.Single;
import java.util.Map;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface VideoService {
  @GET("youtube/v3/search")
  Single<Search> getResult(@QueryMap Map<String, Object> queryMap);
}

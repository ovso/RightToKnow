package io.github.ovso.righttoknow.network;

import io.github.ovso.righttoknow.news.model.NewsResult;
import io.reactivex.Single;
import java.util.HashMap;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by jaeho on 2017. 12. 20
 */

public interface MyApi {

  public final static String BASE_URL = "https://openapi.naver.com";

  @GET("/v1/search/news") Single<NewsResult> getNews(@QueryMap HashMap<String, String> queryMap);

  @GET("/v1/search/news") Single<NewsResult> getNews(@Query("query") String query,
      @Query("display") int display, @Query("start") int start, @Query("sort") String sort);
}

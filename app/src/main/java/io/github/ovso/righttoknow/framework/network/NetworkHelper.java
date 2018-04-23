package io.github.ovso.righttoknow.framework.network;

import android.content.Context;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.framework.network.api.NewsApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jaeho on 2017. 12. 20
 */

public class NetworkHelper {

  private String baseUrl;
  protected Context context;

  public NetworkHelper(Context context, String baseUrl) {
    this.context = context;
    this.baseUrl = baseUrl;
  }

  protected NewsApi getMyApi() {
    final Retrofit retrofit = createRetrofit();
    return retrofit.create(NewsApi.class);
  }

  private Retrofit createRetrofit() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(createClient())
        .build();

    return retrofit;
  }

  private OkHttpClient createClient() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    httpClient.addInterceptor(chain -> {
      Request original = chain.request();
      Request.Builder requestBuilder = original.newBuilder()
          .header("Content-Type", "plain/text")
          .addHeader("X-Naver-Client-Id", Security.NAVER_CLIENT_ID.getValue())
          .addHeader("X-Naver-Client-Secret", Security.NAVER_CLIENT_SECRET.getValue());

      Request request = requestBuilder.build();
      return chain.proceed(request);
    });
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    httpClient.addInterceptor(interceptor);
    OkHttpClient client = httpClient.build();
    return client;
  }
}
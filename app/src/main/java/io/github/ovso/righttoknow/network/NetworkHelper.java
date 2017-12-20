package io.github.ovso.righttoknow.network;

import android.content.Context;
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

  protected MyApi getMyApi() {
    final Retrofit retrofit = createRetrofit();
    return retrofit.create(MyApi.class);
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
          .addHeader("X-Naver-Client-Id", "HLSg0Vn_L6Y2S65DbzKP")
          .addHeader("X-Naver-Client-Secret", "oa1k9kyE0E");

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
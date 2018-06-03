package io.github.ovso.righttoknow.framework.network;

import android.content.Context;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jaeho on 2018. 3. 12
 */

public abstract class NetworkHelper2<T> {

  private String baseUrl;
  protected Context context;

  public NetworkHelper2(Context context, String baseUrl) {
    this.context = context;
    this.baseUrl = baseUrl;
  }

  public T getApi() {
    return createRetrofit().create(getApiClass());
  }

  protected abstract Class<T> getApiClass();

  protected abstract Request createRequst(Request original);

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
    httpClient.addInterceptor(
        new Interceptor() {
          @Override public Response intercept(Chain chain) throws IOException {
            return chain.proceed(NetworkHelper2.this.createRequst(chain.request()));
          }
        });
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    httpClient.addInterceptor(interceptor);
    OkHttpClient client = httpClient.build();
    return client;
  }
}
package io.github.ovso.righttoknow.data.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseRequest<T> {
  public final static int TIMEOUT_SECONDS = 7;

  public T getApi() {
    return createRetrofit().create(getApiClass());
  }

  protected abstract Class<T> getApiClass();

  protected abstract Headers createHeaders();

  protected abstract String getBaseUrl();

  private Retrofit createRetrofit() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(createClient())
        .build();

    return retrofit;
  }

  private OkHttpClient createClient() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    httpClient.readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);
    httpClient.connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);
    httpClient.addInterceptor(new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder =
            original.newBuilder()
                .header("Content-Type", "plain/text")
                .headers(BaseRequest.this.createHeaders());
        Request request = requestBuilder.build();
        return chain.proceed(request);
      }
    });
    //HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    //
    //httpClient.addInterceptor(interceptor);
    OkHttpClient client = httpClient.build();
    return client;
  }
}

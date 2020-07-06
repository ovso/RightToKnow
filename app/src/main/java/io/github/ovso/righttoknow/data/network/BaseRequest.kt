package io.github.ovso.righttoknow.data.network

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

abstract class BaseRequest<T> {
  val api: T
    get() = createRetrofit().create(apiClass)

  protected abstract val apiClass: Class<T>?
  protected abstract fun createHeaders(): Headers?
  protected abstract val baseUrl: String?
  private fun createRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(createClient())
      .build()
  }

  private fun createClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.readTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
    httpClient.connectTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
    httpClient.addInterceptor(object : Interceptor {
      @Throws(IOException::class)
      override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
          .header("Content-Type", "plain/text")
          .headers(createHeaders()!!)
        val request = requestBuilder.build()
        return chain.proceed(request)
      }
    })
    //HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    //
    //httpClient.addInterceptor(interceptor);
    return httpClient.build()
  }

  companion object {
    const val TIMEOUT_SECONDS = 7
  }
}
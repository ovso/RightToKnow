package io.github.ovso.righttoknow.framework.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

abstract class NetworkHelper2<T>(protected var context: Context, private val baseUrl: String) {
  val api: T
    get() = createRetrofit().create(apiClass)

  protected abstract val apiClass: Class<T>
  protected abstract fun createRequst(original: Request?): Request?
  private fun createRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(createClient())
      .build()
  }

  private fun createClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(
      object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
          return chain.proceed(createRequst(chain.request())!!)
        }
      })
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    httpClient.addInterceptor(interceptor)
    return httpClient.build()
  }

}
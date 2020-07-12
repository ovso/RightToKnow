package io.github.ovso.righttoknow.framework.network

import android.content.Context
import io.github.ovso.righttoknow.Security
import io.github.ovso.righttoknow.framework.network.api.NewsApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

open class NetworkHelper(protected var context: Context, private val baseUrl: String) {
  protected val myApi: NewsApi
    protected get() {
      val retrofit = createRetrofit()
      return retrofit.create(NewsApi::class.java)
    }

  private fun createRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(createClient())
      .build()
  }

  private fun createClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(object : Interceptor {
      @Throws(IOException::class)
      override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
          .header("Content-Type", "plain/text")
          .addHeader("X-Naver-Client-Id", Security.NAVER_CLIENT_ID.value)
          .addHeader("X-Naver-Client-Secret", Security.NAVER_CLIENT_SECRET.value)
        val request = requestBuilder.build()
        return chain.proceed(request)
      }
    })
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    httpClient.addInterceptor(interceptor)
    return httpClient.build()
  }

}

package io.github.ovso.righttoknow.data.network

import android.text.TextUtils
import io.github.ovso.righttoknow.Security
import io.github.ovso.righttoknow.data.KeyName
import io.github.ovso.righttoknow.data.network.model.video.Search
import io.reactivex.Single
import okhttp3.Headers
import java.util.*
import kotlin.collections.HashMap

class VideoRequest : BaseRequest<VideoService>() {
  override val apiClass: Class<VideoService>
    protected get() = VideoService::class.java

  override fun createHeaders(): Headers? {
    return Headers.Builder().build()
  }

  override val baseUrl: String
    protected get() = ApiEndPoint.SEARCH.url

  fun getResult(q: String, pageToken: String?): Single<Search> {
    return api.getResult(createQueryMap(q, pageToken))
  }

  private fun createQueryMap(q: String, pageToken: String?): HashMap<String, Any> {
    val queryMap: HashMap<String, Any> = HashMap()
    if (!TextUtils.isEmpty(q)) {
      queryMap[KeyName.Q.get()] = q
    }
    queryMap[KeyName.MAX_RESULTS.get()] = 50
    queryMap[KeyName.ORDER.get()] = "date"
    queryMap[KeyName.TYPE.get()] = "video"
    queryMap[KeyName.VIDEO_SYNDICATED.get()] = "true"
    queryMap[KeyName.KEY.get()] = Security.GOOGLE_API_KEY.value
    queryMap[KeyName.PART.get()] = "snippet"
    pageToken?.let {
      queryMap[KeyName.PAGE_TOKEN.get()] = it
    }
    return queryMap
  }
}

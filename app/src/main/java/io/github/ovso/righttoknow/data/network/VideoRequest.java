package io.github.ovso.righttoknow.data.network;

import android.text.TextUtils;
import io.github.ovso.righttoknow.Security;
import io.github.ovso.righttoknow.data.KeyName;
import io.github.ovso.righttoknow.data.network.model.video.Search;
import io.reactivex.Single;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import okhttp3.Headers;

public class VideoRequest extends BaseRequest<VideoService> {

  @Inject public VideoRequest() {

  }

  @Override protected Class<VideoService> getApiClass() {
    return VideoService.class;
  }

  @Override protected Headers createHeaders() {

    return new Headers.Builder().build();
  }

  @Override protected String getBaseUrl() {
    return ApiEndPoint.SEARCH.getUrl();
  }

  public Single<Search> getResult(String q, String pageToken) {
    return getApi().getResult(createQueryMap(q, pageToken));
  }

  private Map<String, Object> createQueryMap(String q, String pageToken) {
    Map<String, Object> queryMap = new HashMap<>();
    if (!TextUtils.isEmpty(q)) {
      queryMap.put(KeyName.Q.get(), q);
    }
    queryMap.put(KeyName.MAX_RESULTS.get(), 50);
    queryMap.put(KeyName.ORDER.get(), "date");
    queryMap.put(KeyName.TYPE.get(), "video");
    queryMap.put(KeyName.VIDEO_SYNDICATED.get(), "true");
    queryMap.put(KeyName.KEY.get(), Security.GOOGLE_API_KEY.getValue());
    queryMap.put(KeyName.PART.get(), "snippet");
    if (!TextUtils.isEmpty(pageToken)) {
      queryMap.put(KeyName.PAGE_TOKEN.get(), pageToken);
    }
    return queryMap;
  }
}
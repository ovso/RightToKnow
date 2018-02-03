package io.github.ovso.righttoknow.news;

import android.content.Context;
import android.support.annotation.StringRes;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.network.NetworkHelper;
import io.github.ovso.righttoknow.news.model.NewsResult;
import io.reactivex.Single;

/**
 * Created by jaeho on 2017. 12. 20
 */

public class NewsNetwork extends NetworkHelper {
  public NewsNetwork(Context context, String baseUrl) {
    super(context, baseUrl);
  }

  public Single<NewsResult> getNews(@StringRes int resId) {
    String query = context.getString(resId);
    int display = 50;
    int start = 1;
    String sort = context.getString(R.string.api_sort);
    return getMyApi().getNews(query, display, start, sort);
  }
}

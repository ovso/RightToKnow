package io.github.ovso.righttoknow.news;

import io.github.ovso.righttoknow.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.adapter.OnRecyclerItemClickListener;
import io.github.ovso.righttoknow.news.vo.News;

/**
 * Created by jaeho on 2017. 9. 2
 */

public interface NewsAdapterDataModel extends BaseAdapterDataModel<News> {
  void setOnItemClickListener(OnRecyclerItemClickListener<News> listener);
}

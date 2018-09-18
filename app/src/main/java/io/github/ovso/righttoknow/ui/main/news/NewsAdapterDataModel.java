package io.github.ovso.righttoknow.ui.main.news;

import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.ui.main.news.model.News;

/**
 * Created by jaeho on 2017. 9. 2
 */

public interface NewsAdapterDataModel extends BaseAdapterDataModel<News> {
  void setOnItemClickListener(OnNewsRecyclerItemClickListener<News> listener);
}

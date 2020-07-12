package io.github.ovso.righttoknow.ui.main.news

import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel
import io.github.ovso.righttoknow.ui.main.news.model.News

interface NewsAdapterDataModel : BaseAdapterDataModel<News> {
  fun setOnItemClickListener(listener: OnNewsRecyclerItemClickListener<News>)
}
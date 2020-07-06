package io.github.ovso.righttoknow.data.network.model.video

data class Search(
  val kind: String,
  val etag: String,
  val nextPageToken: String,
  val regionCode: String,
  val pageInfo: SearchPageInfo,
  val items: List<SearchItem>
)
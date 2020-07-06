package io.github.ovso.righttoknow.data.network.model.video

data class SearchItem(
  val kind: String,
  val etag: String,
  val id: SearchItemId,
  val snippet: Snippet
)
package io.github.ovso.righttoknow.data.network.model.video

import java.util.*

class Snippet(
  val publishedAt: Date,
  val channelId: String,
  val title: String,
  val description: String,
  val thumbnails: SearchThumbnails,
  val channelTitle: String,
  val liveBroadcastContent: String
)
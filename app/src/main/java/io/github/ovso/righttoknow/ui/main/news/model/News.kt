package io.github.ovso.righttoknow.ui.main.news.model

import java.io.Serializable

data class News(
  val title: String,
  val originallink: String,
  val link: String,
  val description: String,
  val pubDate: String
) : Serializable
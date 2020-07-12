package io.github.ovso.righttoknow.framework.adapter

interface BaseAdapterDataModel<T> {
  fun add(item: T)
  fun addAll(items: List<T>)
  fun remove(position: Int): T
  fun getItem(position: Int): T
  fun add(index: Int, item: T)
  val size: Int
  fun clear()
}

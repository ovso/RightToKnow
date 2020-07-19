package io.github.ovso.righttoknow.ui.main.violator.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.*

class Violator {
  var name: String? = null
  var history: String? = null
  var order: String? = null
  var sido: String? = null
  var sigungu: String? = null
  var fac_name: String? = null
  @kotlin.jvm.JvmField
  var address: String? = null
  @kotlin.jvm.JvmField
  var link: String? = null

  companion object {
    @kotlin.jvm.JvmStatic
    @Throws(Exception::class)
    fun convertToItems(doc: Document): List<Violator> {
      val jsonArray = JSONArray()
      val tableElements = doc.select("tbody")
      val trElements = tableElements.select("tr")
      for (trElement in trElements) {
        val tdElements: Elements? = trElement.select("td")
        val `object` = JSONObject()
        `object`.put("order", tdElements?.get(0)?.ownText())
        `object`.put("sido", tdElements?.get(1)?.ownText())
        `object`.put("sigungu", tdElements?.get(2)?.ownText())
        `object`.put("fac_name", tdElements?.get(4)?.ownText())
        `object`.put("address", tdElements?.get(5)?.ownText())
        `object`.put("history", tdElements?.get(6)?.ownText())
        val hrefElements = trElement.select("a[href]")
        val link = hrefElements[0].attr("abs:href")
        `object`.put("link", link)
        `object`.put("name", hrefElements[0].text())
        jsonArray.put(`object`)
      }
      return Gson().fromJson(
        jsonArray.toString(),
        object : TypeToken<ArrayList<Violator?>?>() {}.type
      )
    }

    @kotlin.jvm.JvmStatic
    @Throws(IndexOutOfBoundsException::class)
    fun searchResultItems(
      query: String?,
      all: List<Violator>
    ): List<Violator> {
      val items = ArrayList<Violator>()
      for (violator in all) {
        if (violator.sido!!.contains(query!!)
          || violator.sigungu!!.contains(query)
          || violator.address!!.contains(query)
          || violator.fac_name!!.contains(query)
          || violator.history!!.contains(query)
          || violator.name!!.contains(query)
          || violator.order!!.contains(query)
        ) {
          items.add(violator)
        }
      }
      return items
    }
  }
}
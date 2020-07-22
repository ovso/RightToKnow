package io.github.ovso.righttoknow.ui.main.violationfacility.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import lombok.EqualsAndHashCode
import lombok.Getter
import lombok.ToString
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.nodes.Document
import java.util.*

class VioFac {
  var order = 0
  var sido: String? = null
  var sigungu: String? = null
  var type: String? = null
  var master: String? = null
  var director: String? = null
  var fac_name: String? = null
  var address: String? = null
  var link: String? = null

  companion object {
    @Throws(JSONException::class, IndexOutOfBoundsException::class)
    fun convertToItems(doc: Document): List<VioFac> {
      val jsonArray = JSONArray()
      val tableElements = doc.select("tbody")
      val trElements = tableElements.select("tr")
      for (trElement in trElements) {
        val tdElements = trElement.select("td")
        val `object` = JSONObject()
        `object`.put("order", tdElements[0].ownText())
        `object`.put("sido", tdElements[1].ownText())
        `object`.put("sigungu", tdElements[2].ownText())
        `object`.put("type", tdElements[4].ownText())
        `object`.put("master", tdElements[5].ownText())
        `object`.put("director", tdElements[6].ownText())
        `object`.put("address", tdElements[7].ownText())
        val hrefElements = trElement.select("a[href]")
        val link = hrefElements[0].attr("abs:href")
        `object`.put("link", link)
        `object`.put("fac_name", hrefElements[0].text())
        jsonArray.put(`object`)
      }
      return Gson().fromJson(
        jsonArray.toString(),
        object : TypeToken<ArrayList<VioFac?>?>() {}.type
      )
    }

    @Throws(IndexOutOfBoundsException::class)
    fun searchResultItems(
      query: String?,
      all: List<VioFac>
    ): List<VioFac> {
      val items: MutableList<VioFac> = ArrayList()
      for (vioFac in all) {
        if (vioFac.sido!!.contains(query!!)
          || vioFac.sigungu!!.contains(query)
          || vioFac.address!!.contains(query)
          || vioFac.type!!.contains(query)
          || vioFac.master!!.contains(query)
          || vioFac.director!!.contains(query)
          || vioFac.fac_name!!.contains(query)
        ) {
          items.add(vioFac)
        }
      }
      return items
    }
  }
}
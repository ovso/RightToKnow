package io.github.ovso.righttoknow.ui.main.violator.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Getter @ToString public class Violator {
  public String name;
  public String history;
  public String order;
  public String sido;
  public String sigungu;
  public String fac_name;
  public String address;
  public String link;

  public static List<Violator> convertToItems(Document doc)
      throws Exception {
    JSONArray jsonArray = new JSONArray();

    Elements tableElements = doc.select("tbody");
    Elements trElements = tableElements.select("tr");

    for (Element trElement : trElements) {
      Elements tdElements = trElement.select("td");
      JSONObject object = new JSONObject();
      object.put("order", tdElements.get(0).ownText());
      object.put("sido", tdElements.get(1).ownText());
      object.put("sigungu", tdElements.get(2).ownText());
      object.put("fac_name", tdElements.get(4).ownText());
      object.put("address", tdElements.get(5).ownText());
      object.put("history", tdElements.get(6).ownText());

      Elements hrefElements = trElement.select("a[href]");
      String link = hrefElements.get(0).attr("abs:href");
      object.put("link", link);
      object.put("name", hrefElements.get(0).text());
      jsonArray.put(object);
    }
    return new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Violator>>() {
    }.getType());
  }

  public static List<Violator> searchResultItems(String query, List<Violator> all)
      throws IndexOutOfBoundsException {
    final ArrayList<Violator> items = new ArrayList<>();
    for (Violator violator : all) {
      if (violator.sido.contains(query)
          || violator.sigungu.contains(query)
          || violator.address.contains(query)
          || violator.fac_name.contains(query)
          || violator.history.contains(query)
          || violator.name.contains(query)
          || violator.order.contains(query)) {
        items.add(violator);
      }
    }
    return items;
  }
}
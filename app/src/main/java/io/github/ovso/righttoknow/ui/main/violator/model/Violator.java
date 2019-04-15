package io.github.ovso.righttoknow.ui.main.violator.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by jaeho on 2017. 8. 3
 */

@Getter @ToString public class Violator {
  private String name;
  private String history;
  private String order;
  private String sido;
  private String sigungu;
  private String fac_name;
  private String address;
  private String link;

  public static List<Violator> convertToItems(Document doc)
      throws JSONException, IOException, IndexOutOfBoundsException {
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
      if (violator.getSido().contains(query)
          || violator.getSigungu().contains(query)
          || violator.getAddress().contains(query)
          || violator.getFac_name().contains(query)
          || violator.getHistory().contains(query)
          || violator.getName().contains(query)
          || violator.getOrder().contains(query)) {
        items.add(violator);
      }
    }
    return items;
  }
}
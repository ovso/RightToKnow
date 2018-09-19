package io.github.ovso.righttoknow.ui.main.violationfacility.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by jaeho on 2017. 8. 1
 */

@Getter @ToString @EqualsAndHashCode(callSuper = false) public class VioFac {
  private int order;
  private String sido;
  private String sigungu;
  private String type;
  private String master;
  private String director;
  private String fac_name;
  private String address;
  private String link;

  public static List<VioFac> convertToItems(Document doc)
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
      object.put("type", tdElements.get(4).ownText());
      object.put("master", tdElements.get(5).ownText());
      object.put("director", tdElements.get(6).ownText());
      object.put("address", tdElements.get(7).ownText());

      Elements hrefElements = trElement.select("a[href]");
      String link = hrefElements.get(0).attr("abs:href");
      object.put("link", link);
      object.put("fac_name", hrefElements.get(0).text());

      jsonArray.put(object);
    }

    return new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<VioFac>>() {
    }.getType());
  }

  public static List<VioFac> searchResultItems(String query, List<VioFac> all)
      throws IndexOutOfBoundsException {
    final List<VioFac> items = new ArrayList<>();
    for (VioFac vioFac : all) {
      if (vioFac.getSido().contains(query)
          || vioFac.getSigungu().contains(query)
          || vioFac.getAddress().contains(query)
          || vioFac.getType().contains(query)
          || vioFac.getMaster().contains(query)
          || vioFac.getDirector().contains(query)
          || vioFac.getFac_name().contains(query)) {
        items.add(vioFac);
      }
    }
    return items;
  }
}

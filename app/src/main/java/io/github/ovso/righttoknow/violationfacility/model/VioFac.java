package io.github.ovso.righttoknow.violationfacility.model;

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
      throws JSONException, IOException {
    JSONArray jsonArray = new JSONArray();
    JSONArray linkJsonArray = new JSONArray();

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
      JSONObject linkObject = new JSONObject();
      linkObject.put("link", link);
      linkJsonArray.put(linkObject);
    }

    return new Gson().fromJson(jsonArray.toString(),
        new TypeToken<ArrayList<VioFac>>() {
        }.getType());
  }

  /*
  private static void testMethod(JSONArray linkJsonArray) throws JSONException, IOException {
    long start = System.currentTimeMillis();

    for (int i = 0; i < linkJsonArray.length(); i++) {
      String detailUrl = linkJsonArray.getJSONObject(i).getString("link");
      Document detailDoc = Jsoup.connect(detailUrl).get();
      Elements tbodyElements = detailDoc.body().select("tbody");
      Elements trElements = tbodyElements.select("tr");
      Timber.d("trElementsSize = " + trElements.size());
      Elements tdElements = trElements.get(0).select("td");
      String sido = tdElements.get(0).ownText();
      String sigungu = tdElements.get(1).ownText();
      String type = tdElements.get(2).ownText();
      //Timber.d(sido + ", " + sigungu + ", " + type);
    }
    long end = System.currentTimeMillis();
    long distance = end - start;
    long second = TimeUnit.MILLISECONDS.toSeconds(distance);
    Timber.d("second = " + second);
  }
  */
}

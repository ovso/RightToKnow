package io.github.ovso.righttoknow.ui.main.violation.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import timber.log.Timber;

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
    String json = jsonArray.toString();
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

  public static JSONArray toJson(Document document) throws Exception {
    JSONArray jsonArray = new JSONArray();
    Map<String, Object> map = new HashMap<>();
    Elements tableElements = document.select("tbody");
    Elements trElements = tableElements.select("tr");

    for (Element trElement : trElements) {

      Elements tdElements = trElement.select("td");
      JSONObject object = new JSONObject();

      map.put("order", tdElements.get(0).ownText());
      map.put("sido", tdElements.get(1).ownText());
      map.put("sigungu", tdElements.get(2).ownText());
      map.put("type", tdElements.get(4).ownText());
      map.put("master", tdElements.get(5).ownText());
      map.put("director", tdElements.get(6).ownText());
      map.put("address", tdElements.get(7).ownText());
      Elements hrefElements = trElement.select("a[href]");
      String link = hrefElements.get(0).attr("abs:href");
      map.put("link", link);

      map.put("fac_name", hrefElements.get(0).text());
      jsonArray.put(new JSONObject(map));
    }

    return jsonArray;
  }

  public static JSONObject toDetailJson(Document doc) throws JSONException, IOException {
    Elements tbodyElements = doc.body().select("tbody");
    Elements trElements = tbodyElements.select("tr");
    Timber.d("trElementsSize = " + trElements.size());

    // 시도, 시군구, 어린이집 유형
    Elements tdElements = trElements.get(0).select("td");
    String sido = tdElements.get(0).ownText();
    String sigungu = tdElements.get(1).ownText();
    String type = tdElements.get(2).ownText();

    Timber.d("sido = " + sido);
    Timber.d("sigungu = " + sigungu);
    Timber.d("type = " + type);

    //현재 어린이집명, 현재 대표자, 현재 원장
    String nowFacName = trElements.get(1).select("td").get(0).ownText();
    String nowMaster = trElements.get(1).select("td").get(1).ownText();
    String nowDirector = trElements.get(1).select("td").get(2).ownText();
    Timber.d("nowFacName = " + nowFacName);
    Timber.d("nowMaster = " + nowMaster);
    Timber.d("nowDirector = " + nowDirector);

    //위반당시 어린이집명, 위반당시 대표자, 위반당시 원장
    String vioFacName = trElements.get(2).select("td").get(0).ownText();
    String vioMaster = trElements.get(2).select("td").get(1).ownText();
    String vioDirector = trElements.get(2).select("td").get(2).ownText();
    Timber.d("vioFacName = " + vioFacName);
    Timber.d("vioMaster = " + vioMaster);
    Timber.d("vioDirector = " + vioDirector);

    // 주소
    String address = trElements.get(3).select("td").get(0).ownText();
    // 위반행위
    String action = trElements.get(4).select("td").get(0).ownText();
    // 처분내용
    String disposal = trElements.get(5).select("td").get(0).ownText();
    Timber.d("address = " + address);
    Timber.d("action = " + action);
    Timber.d("disposal = " + disposal);

    JSONObject jsonObject = new JSONObject();

    jsonObject.put("sido", sido);
    jsonObject.put("sigungu", sigungu);
    jsonObject.put("type", type);

    jsonObject.put("nowFacName", nowFacName);
    jsonObject.put("nowMaster", nowFacName);
    jsonObject.put("nowDirector", nowDirector);

    jsonObject.put("vioFacName", vioFacName);
    jsonObject.put("vioMaster", vioFacName);
    jsonObject.put("vioDirector", vioDirector);

    jsonObject.put("address", address);
    jsonObject.put("action", action);
    jsonObject.put("disposal", disposal);

    return jsonObject;
  }

}

package io.github.ovso.righttoknow.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class DocumentParse {
  private DocumentParse() {
  }

  public static JsonArray violation(Document doc) {
    Elements tableElements = doc.select("tbody");
    Elements trElements = tableElements.select("tr");
    final JsonArray jsonArray = new JsonArray();
    for (Element trElement : trElements) {

      Elements tdElements = trElement.select("td");
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("order", tdElements.get(0).ownText());
      jsonObject.addProperty("sido", tdElements.get(1).ownText());
      jsonObject.addProperty("sigungu", tdElements.get(2).ownText());
      jsonObject.addProperty("type", tdElements.get(4).ownText());
      jsonObject.addProperty("master", tdElements.get(5).ownText());
      jsonObject.addProperty("director", tdElements.get(6).ownText());
      jsonObject.addProperty("address", tdElements.get(7).ownText());
      Elements hrefElements = trElement.select("a[href]");
      String link = hrefElements.get(0).attr("abs:href");
      jsonObject.addProperty("link", link);
      jsonObject.addProperty("fac_name", hrefElements.get(0).text());
      jsonArray.add(jsonObject);
    }

    return jsonArray;
  }

  public static JsonObject violationDetail(Document doc) {
    Elements tbodyElements = doc.body().select("tbody");
    Elements trElements = tbodyElements.select("tr");
    //Timber.d("trElementsSize = " + trElements.size());

    // 시도, 시군구, 어린이집 유형
    Elements tdElements = trElements.get(0).select("td");
    String sido = tdElements.get(0).ownText();
    String sigungu = tdElements.get(1).ownText();
    String type = tdElements.get(2).ownText();

    //Timber.d("sido = " + sido);
    //Timber.d("sigungu = " + sigungu);
    //Timber.d("type = " + type);

    //현재 어린이집명, 현재 대표자, 현재 원장
    String nowFacName = trElements.get(1).select("td").get(0).ownText();
    String nowMaster = trElements.get(1).select("td").get(1).ownText();
    String nowDirector = trElements.get(1).select("td").get(2).ownText();
    //Timber.d("nowFacName = " + nowFacName);
    //Timber.d("nowMaster = " + nowMaster);
    //Timber.d("nowDirector = " + nowDirector);

    //위반당시 어린이집명, 위반당시 대표자, 위반당시 원장
    String vioFacName = trElements.get(2).select("td").get(0).ownText();
    String vioMaster = trElements.get(2).select("td").get(1).ownText();
    String vioDirector = trElements.get(2).select("td").get(2).ownText();

    //Timber.d("vioFacName = " + vioFacName);
    //Timber.d("vioMaster = " + vioMaster);
    //Timber.d("vioDirector = " + vioDirector);

    // 주소
    String address = trElements.get(3).select("td").get(0).ownText();
    // 위반행위
    String action = trElements.get(4).select("td").get(0).ownText();
    // 처분내용
    String disposal = trElements.get(5).select("td").get(0).ownText();

    //Timber.d("address = " + address);
    //Timber.d("action = " + action);
    //Timber.d("disposal = " + disposal);

    JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("sido", sido);
    jsonObject.addProperty("sigungu", sigungu);
    jsonObject.addProperty("type", type);

    jsonObject.addProperty("nowFacName", nowFacName);
    jsonObject.addProperty("nowMaster", nowFacName);
    jsonObject.addProperty("nowDirector", nowDirector);

    jsonObject.addProperty("vioFacName", vioFacName);
    jsonObject.addProperty("vioMaster", vioFacName);
    jsonObject.addProperty("vioDirector", vioDirector);

    jsonObject.addProperty("address", address);
    jsonObject.addProperty("action", action);
    jsonObject.addProperty("disposal", disposal);

    return jsonObject;
  }

  public static JsonArray violators(Document doc) {
    final JsonArray jsonArray = new JsonArray();

    return jsonArray;
  }

  public static JsonArray certified(Document doc) {
    final JsonArray jsonArray = new JsonArray();
    return jsonArray;
  }
}

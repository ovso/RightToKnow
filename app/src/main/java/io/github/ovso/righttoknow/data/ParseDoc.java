package io.github.ovso.righttoknow.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class ParseDoc {
  private ParseDoc() {
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

  public static JsonArray violators(Document doc) {
    final JsonArray jsonArray = new JsonArray();

    return jsonArray;
  }

  public static JsonArray certified(Document doc) {
    final JsonArray jsonArray = new JsonArray();
    return jsonArray;
  }
}

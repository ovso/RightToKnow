package io.github.ovso.righttoknow.certified.model;

import java.io.IOException;
import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 8. 21
 */

@Getter @ToString public class ChildCertifiedDetail {
  private String pdfLink;

  public static String convertToPdfLink(Document doc)
      throws JSONException, IOException, IndexOutOfBoundsException {
    JSONArray jsonArray = new JSONArray();
    Elements tableElements = doc.select("tbody");
    Elements trElements = tableElements.select("tr");
    Elements fileElements = trElements.select("a href");
    Timber.d("fileElements = " + fileElements);
    Timber.d("trElements = " + trElements);
    /*
    for (Element trElement : trElements) {

      Elements tdElements = trElement.select("td");
      JSONObject object = new JSONObject();
      object.put("order", tdElements.get(0).ownText());
      object.put("regDate", tdElements.get(3).ownText());
      object.put("hits", tdElements.get(4).ownText());
      Elements hrefElements = trElement.select("a[href]");
      String onclick = hrefElements.get(0).attr("onclick");
      String BBSGB = onclick.substring(onclick.indexOf("(") + 2, onclick.indexOf("(") + 5);
      String BID = onclick.substring(onclick.indexOf(")") - 6, onclick.indexOf(")") - 1);
      object.put("BBSGB", BBSGB);
      object.put("BID", BID);
      object.put("flag", "SI");
      String title = hrefElements.get(0).attr("title");
      object.put("title", title);
      String link = "/info/cera/community/notice/CertNoticeSl.jsp"
          + "?"
          + "flag=SI"
          + "&BBSGB="
          + BBSGB
          + "&BID="
          + BID;
      object.put("link", link);

      jsonArray.put(object);
    }
    */

    //return new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<ChildCertifiedDetail>>() {
    //}.getType());

    return "";
  }
}

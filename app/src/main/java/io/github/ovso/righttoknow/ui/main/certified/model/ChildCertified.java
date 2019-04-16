package io.github.ovso.righttoknow.ui.main.certified.model;

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
 * Created by jaeho on 2017. 8. 21
 */

@Getter @ToString public class ChildCertified {
  private int order;
  private String title;
  private String regDate;
  private int hits;
  private String link;
  private String BBSGB;
  private String BID;
  private String flag;
  private String downloadUrl;

  public static List<ChildCertified> convertToItems(Document doc)
      throws JSONException, IOException, IndexOutOfBoundsException {
    JSONArray jsonArray = new JSONArray();
    Elements tableElements = doc.select("tbody");
    Elements trElements = tableElements.select("tr");

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
      String downloadUrl = "/cpis/community/common/DownloadBoardFile.jsp"
          + "?"
          + "BBSGB="
          + BBSGB
          + "&BID="
          + BID
          + "&ATCHMNFLSEQ="
          + "1";
      object.put("downloadUrl", downloadUrl);

      jsonArray.put(object);
    }

    return new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<ChildCertified>>() {
    }.getType());
  }
}

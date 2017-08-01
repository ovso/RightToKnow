package io.github.ovso.righttoknow.violation;

import android.util.Log;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by jaeho on 2017. 8. 1
 */

class ViolationInteractor {
  public void req(String url) {
    try {
      tableToJson(url);
    } catch (JSONException e) {

    }
  }

  public String tableToJson(String source) throws JSONException {

    Document doc;
    JSONObject rootJsonObject = new JSONObject();

    try {
      doc = Jsoup.connect(source).get();
      for (Element thead : doc.select("thead")) {
        Elements e = thead.select("tr").get(0).select("th");
        for (Element element : e) {
          String a = element.childNode(0).toString();
        }
      }
      for (Element tbody : doc.select("tbody")) {
        Elements e = tbody.select("tr");
        for (Element element : e) {
          for (Element td : element.select("td")) {
            String a = td.toString();
          }
        }
        Log.d("", tbody.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return rootJsonObject.toString();
  }
}

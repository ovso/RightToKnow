package io.github.ovso.righttoknow.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import io.github.ovso.righttoknow.data.network.model.certified.Certified;
import io.github.ovso.righttoknow.data.network.model.violation.Violation;
import io.github.ovso.righttoknow.data.network.model.violation.ViolationContents;
import io.github.ovso.righttoknow.data.network.model.violators.Violators;
import io.github.ovso.righttoknow.data.network.model.violators.ViolatorsContents;
import io.github.ovso.righttoknow.ui.main.certified.model.ChildCertified;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class DocumentParse {
  private DocumentParse() {
  }

  public static List<Violation> toViolations(Document doc) {
    Elements tableElements = doc.select("tbody");
    Elements trElements = tableElements.select("tr");
    List<Violation> violations = new ArrayList<>();
    for (Element trElement : trElements) {

      Elements tdElements = trElement.select("td");
      Violation violation = new Violation();
      violation.order = tdElements.get(0).ownText();
      violation.sido = tdElements.get(1).ownText();
      violation.sigungu = tdElements.get(2).ownText();
      violation.type = tdElements.get(4).ownText();
      violation.master = tdElements.get(5).ownText();
      violation.director = tdElements.get(6).ownText();
      violation.address = tdElements.get(7).ownText();
      Elements hrefElements = trElement.select("a[href]");
      String link = hrefElements.get(0).attr("abs:href");
      violation.link = link;
      violation.vio_ccc = hrefElements.get(0).text();

      violations.add(violation);
    }

    return violations;
  }

  public static ViolationContents toViolationContents(Document doc) {
    Elements tbodyElements = doc.body().select("tbody");
    Elements trElements = tbodyElements.select("tr");

    // 시도, 시군구, 어린이집 유형
    Elements tdElements = trElements.get(0).select("td");

    ViolationContents contents = new ViolationContents();

    contents.sido = tdElements.get(0).ownText();
    contents.sigungu = tdElements.get(1).ownText();
    contents.type = tdElements.get(2).ownText();

    contents.now_ccc = trElements.get(1).select("td").get(0).ownText();
    contents.now_master = trElements.get(1).select("td").get(1).ownText();
    contents.now_director = trElements.get(1).select("td").get(2).ownText();
    //위반당시 어린이집명, 위반당시 대표자, 위반당시 원장
    contents.vio_ccc = trElements.get(2).select("td").get(0).ownText();
    contents.vio_master = trElements.get(2).select("td").get(1).ownText();
    contents.vio_director = trElements.get(2).select("td").get(2).ownText();
    // 주소
    contents.address = trElements.get(3).select("td").get(0).ownText();
    // 위반행위
    contents.vio_action = trElements.get(4).select("td").get(0).ownText();
    // 처분내용
    contents.disposition = trElements.get(5).select("td").get(0).ownText();

    return contents;
  }

  public static List<Violators> toViolators(Document doc) {
    List<Violators> items = new ArrayList<>();

    Elements tableElements = doc.select("tbody");
    Elements trElements = tableElements.select("tr");

    for (Element trElement : trElements) {
      Elements tdElements = trElement.select("td");
      JSONObject object = new JSONObject();
      Violators vio = new Violators();
      vio.order = tdElements.get(0).ownText();
      vio.sido = tdElements.get(1).ownText();
      vio.sigungu = tdElements.get(2).ownText();
      vio.vio_ccc = tdElements.get(4).ownText();
      vio.address = tdElements.get(5).ownText();
      vio.history = tdElements.get(6).ownText();
      Elements hrefElements = trElement.select("a[href]");
      String link = hrefElements.get(0).attr("abs:href");
      vio.link = link;
      vio.vio_name = hrefElements.get(0).text();
    }

    return items;
  }

  public static ViolatorsContents toViolatorsContents(Document doc) {
    ViolatorsContents c = new ViolatorsContents();

    Elements tbodyElements = doc.body().select("tbody");
    Elements trElements = tbodyElements.select("tr");
    Elements tdElements = trElements.get(0).select("td");

    c.sido = tdElements.get(0).ownText();
    c.sigungu = tdElements.get(1).ownText();

    c.name = trElements.get(1).select("td").get(0).ownText();
    c.vio_ccc = trElements.get(1).select("td").get(1).ownText();

    c.history = trElements.get(2).select("td").get(0).ownText();
    c.address = trElements.get(2).select("td").get(1).ownText();

    // 위반행위
    c.vio_action = trElements.get(3).select("td").get(0).ownText();
    // 처분내용
    c.disposition = trElements.get(4).select("td").get(0).ownText();

    return c;
  }

  public static List<Certified> toCertified(Document doc) {

    List<Certified> items = new ArrayList<>();
    Elements tableElements = doc.select("tbody");
    Elements trElements = tableElements.select("tr");

    for (Element trElement : trElements) {
      Certified c = new Certified();
      Elements tdElements = trElement.select("td");
      c.order = tdElements.get(0).ownText();
      c.reg_date = tdElements.get(3).ownText();
      c.hits = tdElements.get(4).ownText();
      Elements hrefElements = trElement.select("a[href]");
      String onclick = hrefElements.get(0).attr("onclick");
      String BBSGB = onclick.substring(onclick.indexOf("(") + 2, onclick.indexOf("(") + 5);
      String BID = onclick.substring(onclick.indexOf(")") - 6, onclick.indexOf(")") - 1);
      c.bbsgb = BBSGB;
      c.bid = BID;
      c.flag = "SI";
      c.title = hrefElements.get(0).attr("title");
      String link = "/info/cera/community/notice/CertNoticeSl.jsp"
          + "?"
          + "flag=SI"
          + "&BBSGB="
          + BBSGB
          + "&BID="
          + BID;
      c.link = link;
      String downloadUrl = "/cpis/community/common/DownloadBoardFile.jsp"
          + "?"
          + "BBSGB="
          + BBSGB
          + "&BID="
          + BID
          + "&ATCHMNFLSEQ="
          + "1";
      c.download_url = downloadUrl;

      items.add(c);
    }

    return items;
  }
}

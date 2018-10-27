package io.github.ovso.righttoknow.data.network.model.certified;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Certified {

  public String order;
  public String reg_date;
  public String hits;
  public String bbsgb;
  public String bid;
  public String flag;
  public String title;
  public String link;
  public String download_url;

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
      String downloadUrl = "http://info.childcare.go.kr/cpis/community/common/DownloadBoardFile.jsp"
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

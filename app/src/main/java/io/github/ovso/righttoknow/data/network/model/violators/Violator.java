package io.github.ovso.righttoknow.data.network.model.violators;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Violator {

  public String order;
  public String sido;
  public String sigungu;
  public String vio_ccc;
  public String address;
  public String history;
  public String link;
  public String vio_name;
  public ViolatorContents contents;

  public static List<Violator> toObjects(Document doc) {
    List<Violator> items = new ArrayList<>();

    Elements tableElements = doc.select("tbody");
    Elements trElements = tableElements.select("tr");

    for (Element trElement : trElements) {
      Elements tdElements = trElement.select("td");
      Violator vio = new Violator();
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
      items.add(vio);
    }

    return items;
  }

}

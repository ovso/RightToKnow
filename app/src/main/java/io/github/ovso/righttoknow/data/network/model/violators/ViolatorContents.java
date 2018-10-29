package io.github.ovso.righttoknow.data.network.model.violators;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ViolatorContents {
  public String sido;
  public String sigungu;
  public String name;
  public String vio_ccc;
  public String history;
  public String address;
  public String vio_action;
  public String disposition;

  public static ViolatorContents toObject(Document doc) {
    ViolatorContents c = new ViolatorContents();

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
}

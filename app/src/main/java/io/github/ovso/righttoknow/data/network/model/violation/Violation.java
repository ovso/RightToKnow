package io.github.ovso.righttoknow.data.network.model.violation;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@IgnoreExtraProperties public class Violation {
  public String order;
  public String sido;
  public String sigungu;
  public String type;
  public String master;
  public String director;
  public String address;
  public String link;
  public String vio_ccc;
  public ViolationContents contents;

  public static List<Violation> toObjects(Document doc) {
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

}

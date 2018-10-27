package io.github.ovso.righttoknow.data.network.model.violation;

import com.google.firebase.database.IgnoreExtraProperties;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@IgnoreExtraProperties public class ViolationContents {
  public String sido;
  public String sigungu;
  public String type;
  public String now_ccc;
  public String now_master;
  public String now_director;
  public String vio_ccc;
  public String vio_master;
  public String vio_director;
  public String address;
  public String vio_action;
  public String disposition;

  public static ViolationContents toObject(Document doc) {
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

}
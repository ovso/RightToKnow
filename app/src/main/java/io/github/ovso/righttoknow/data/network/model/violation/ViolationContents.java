package io.github.ovso.righttoknow.data.network.model.violation;

import android.content.res.Resources;
import com.google.firebase.database.IgnoreExtraProperties;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.ui.violation_contents.model.VioFacDe;
import lombok.ToString;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.parceler.Parcel;

@Parcel @ToString @IgnoreExtraProperties public class ViolationContents {
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

  public static String toFormatedText(ViolationContents o) {
    Resources res = App.getInstance().getResources();

    StringBuilder builder = new StringBuilder();
    //sido
    builder.append(res.getString(R.string.detail_sido)).append(o.sido);
    builder.append("\n\n");
    //sigungu
    builder.append(res.getString(R.string.detail_sigungu)).append(o.sigungu);
    builder.append("\n\n");
    //type
    builder.append(res.getString(R.string.detail_type)).append(o.type);
    builder.append("\n\n");
    //now
    builder.append(res.getString(R.string.detail_now));
    builder.append("\n");
    builder.append(res.getString(R.string.detail_daycare_center)).append(o.now_ccc);
    builder.append("\n");
    builder.append(res.getString(R.string.detail_boss)).append(o.now_master);
    builder.append("\n");
    builder.append(res.getString(R.string.detail_director)).append(o.now_director);
    builder.append("\n\n");
    //old
    builder.append(res.getString(R.string.detail_vio_old));
    builder.append("\n");
    builder.append(res.getString(R.string.detail_daycare_center)).append(o.vio_ccc);
    builder.append("\n");
    builder.append(res.getString(R.string.detail_boss)).append(o.vio_master);
    builder.append("\n");
    builder.append(res.getString(R.string.detail_director)).append(o.vio_director);
    builder.append("\n\n");
    //address
    builder.append(res.getString(R.string.detail_address)).append(o.address);
    builder.append("\n\n");
    //action
    builder.append(res.getString(R.string.detail_vio_action)).append(o.vio_action);

    builder.append("\n\n");
    //disposal
    builder.append(res.getString(R.string.detail_vio_disposal)).append(o.disposition);
    builder.append("\n");
    builder.append("\n");
    return builder.toString();
  }
}
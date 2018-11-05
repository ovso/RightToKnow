package io.github.ovso.righttoknow.data.network.model.violators;

import android.content.res.Resources;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.R;
import lombok.ToString;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.parceler.Parcel;

@Parcel @ToString public class ViolatorContents {
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

  public static String toFormatedText(ViolatorContents violator) {
    Resources res = App.getInstance().getResources();
    StringBuilder builder = new StringBuilder();
    //sido
    builder.append(res.getString(R.string.detail_sido)).append(violator.sido);
    builder.append("\n\n");
    //sigungu
    builder.append(res.getString(R.string.detail_sigungu)).append(violator.sigungu);
    builder.append("\n\n");
    //name
    builder.append(res.getString(R.string.detail_violator_name)).append(violator.name);
    builder.append("\n\n");
    //old center name
    builder.append(res.getString(R.string.detail_vio_old_center_name))
        .append(violator.vio_ccc);
    builder.append("\n\n");
    //history
    builder.append(res.getString(R.string.detail_violation_history)).append(violator.history);
    builder.append("\n\n");
    //address
    builder.append(res.getString(R.string.detail_address)).append(violator.address);
    builder.append("\n\n");
    //action
    builder.append("\n");
    builder.append("\n");
    //disposal
    builder.append("\n");
    builder.append("\n");
    return builder.toString();
  }
}

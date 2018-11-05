package io.github.ovso.righttoknow.ui.violation_contents.model;

import android.content.res.Resources;
import com.google.gson.Gson;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.App;
import java.io.IOException;
import lombok.Getter;
import lombok.ToString;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import timber.log.Timber;

/**
 * Created by jaeho on 2018. 3. 14
 */

@ToString @Getter public class ViolatorDe {

  private String sido;
  private String sigungu;
  private String name;
  private String facName;
  private String history;
  private String address;
  private String action;
  private String disposal;

  public static ViolatorDe convertToItem(Document doc) throws JSONException, IOException {
    Elements tbodyElements = doc.body().select("tbody");
    Elements trElements = tbodyElements.select("tr");
    Timber.d("trElementsSize = " + trElements.size());

    Elements tdElements = trElements.get(0).select("td");
    String sido = tdElements.get(0).ownText();
    String sigungu = tdElements.get(1).ownText();

    Timber.d("sido = " + sido);
    Timber.d("sigungu = " + sigungu);

    String name = trElements.get(1).select("td").get(0).ownText();
    String facName = trElements.get(1).select("td").get(1).ownText();
    Timber.d("name = " + name);
    Timber.d("facName = " + facName);

    String history = trElements.get(2).select("td").get(0).ownText();
    String address = trElements.get(2).select("td").get(1).ownText();

    // 위반행위
    String action = trElements.get(3).select("td").get(0).ownText();
    // 처분내용
    String disposal = trElements.get(4).select("td").get(0).ownText();
    Timber.d("address = " + address);
    Timber.d("action = " + action);
    Timber.d("disposal = " + disposal);

    JSONObject jsonObject = new JSONObject();

    jsonObject.put("sido", sido);
    jsonObject.put("sigungu", sigungu);

    jsonObject.put("name", name);
    jsonObject.put("facName", facName);
    jsonObject.put("history", history);
    jsonObject.put("address", address);
    jsonObject.put("action", action);
    jsonObject.put("disposal", disposal);

    return new Gson().fromJson(jsonObject.toString(), ViolatorDe.class);
  }

  public static String getContents(ViolatorDe violator) {
    Resources res = App.getInstance().getResources();
    StringBuilder builder = new StringBuilder();
    //sido
    builder.append(res.getString(R.string.detail_sido)).append(violator.getSido());
    builder.append("\n\n");
    //sigungu
    builder.append(res.getString(R.string.detail_sigungu)).append(violator.getSigungu());
    builder.append("\n\n");
    //name
    builder.append(res.getString(R.string.detail_violator_name)).append(violator.getName());
    builder.append("\n\n");
    //old center name
    builder.append(res.getString(R.string.detail_vio_old_center_name))
        .append(violator.getFacName());
    builder.append("\n\n");
    //history
    builder.append(res.getString(R.string.detail_violation_history)).append(violator.getHistory());
    builder.append("\n\n");
    //address
    builder.append(res.getString(R.string.detail_address)).append(violator.getAddress());
    builder.append("\n\n");
    //action
    //builder.append(res.getString(R.string.detail_violator_action)).append(violator.getAction());
    builder.append("\n");
    builder.append("\n");
    //disposal
    //builder.append(res.getString(R.string.detail_violator_disposal)).append(violator.getDisposal());
    builder.append("\n");
    builder.append("\n");
    return builder.toString();
  }
}

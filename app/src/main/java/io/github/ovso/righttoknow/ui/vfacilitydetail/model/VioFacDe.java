package io.github.ovso.righttoknow.ui.vfacilitydetail.model;

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
 * Created by jaeho on 2017. 8. 2
 */

@Getter @ToString public class VioFacDe {
  private String sido;
  private String sigungu;
  private String type;

  private String nowFacName;
  private String nowMaster;
  private String nowDirector;

  private String vioFacName;
  private String vioMaster;
  private String vioDirector;

  private String address;

  private String action;
  private String disposal;

  public static VioFacDe convertToItem(Document doc) throws JSONException, IOException {
    Elements tbodyElements = doc.body().select("tbody");
    Elements trElements = tbodyElements.select("tr");
    Timber.d("trElementsSize = " + trElements.size());

    // 시도, 시군구, 어린이집 유형
    Elements tdElements = trElements.get(0).select("td");
    String sido = tdElements.get(0).ownText();
    String sigungu = tdElements.get(1).ownText();
    String type = tdElements.get(2).ownText();

    Timber.d("sido = " + sido);
    Timber.d("sigungu = " + sigungu);
    Timber.d("type = " + type);

    //현재 어린이집명, 현재 대표자, 현재 원장
    String nowFacName = trElements.get(1).select("td").get(0).ownText();
    String nowMaster = trElements.get(1).select("td").get(1).ownText();
    String nowDirector = trElements.get(1).select("td").get(2).ownText();
    Timber.d("nowFacName = " + nowFacName);
    Timber.d("nowMaster = " + nowMaster);
    Timber.d("nowDirector = " + nowDirector);

    //위반당시 어린이집명, 위반당시 대표자, 위반당시 원장
    String vioFacName = trElements.get(2).select("td").get(0).ownText();
    String vioMaster = trElements.get(2).select("td").get(1).ownText();
    String vioDirector = trElements.get(2).select("td").get(2).ownText();
    Timber.d("vioFacName = " + vioFacName);
    Timber.d("vioMaster = " + vioMaster);
    Timber.d("vioDirector = " + vioDirector);

    // 주소
    String address = trElements.get(3).select("td").get(0).ownText();
    // 위반행위
    String action = trElements.get(4).select("td").get(0).ownText();
    // 처분내용
    String disposal = trElements.get(5).select("td").get(0).ownText();
    Timber.d("address = " + address);
    Timber.d("action = " + action);
    Timber.d("disposal = " + disposal);

    JSONObject jsonObject = new JSONObject();

    jsonObject.put("sido", sido);
    jsonObject.put("sigungu", sigungu);
    jsonObject.put("type", type);

    jsonObject.put("nowFacName", nowFacName);
    jsonObject.put("nowMaster", nowFacName);
    jsonObject.put("nowDirector", nowDirector);

    jsonObject.put("vioFacName", vioFacName);
    jsonObject.put("vioMaster", vioFacName);
    jsonObject.put("vioDirector", vioDirector);

    jsonObject.put("address", address);
    jsonObject.put("action", action);
    jsonObject.put("disposal", disposal);

    return new Gson().fromJson(jsonObject.toString(), VioFacDe.class);
  }

  public static String getContents(VioFacDe o) {
    Resources res = App.getInstance().getResources();

    StringBuilder builder = new StringBuilder();
    //sido
    builder.append(res.getString(R.string.detail_sido)).append(o.getSido());
    builder.append("\n\n");
    //sigungu
    builder.append(res.getString(R.string.detail_sigungu)).append(o.getSigungu());
    builder.append("\n\n");
    //type
    builder.append(res.getString(R.string.detail_type)).append(o.getType());
    builder.append("\n\n");
    //now
    builder.append(res.getString(R.string.detail_now));
    builder.append("\n");
    builder.append(res.getString(R.string.detail_daycare_center)).append(o.getNowFacName());
    builder.append("\n");
    builder.append(res.getString(R.string.detail_boss)).append(o.getNowMaster());
    builder.append("\n");
    builder.append(res.getString(R.string.detail_director)).append(o.getNowDirector());
    builder.append("\n\n");
    //old
    builder.append(res.getString(R.string.detail_vio_old));
    builder.append("\n");
    builder.append(res.getString(R.string.detail_daycare_center)).append(o.getVioFacName());
    builder.append("\n");
    builder.append(res.getString(R.string.detail_boss)).append(o.getVioMaster());
    builder.append("\n");
    builder.append(res.getString(R.string.detail_director)).append(o.getVioDirector());
    builder.append("\n\n");
    //address
    builder.append(res.getString(R.string.detail_address)).append(o.getAddress());
    builder.append("\n\n");
    //action
    builder.append(res.getString(R.string.detail_vio_action)).append(o.getAction());

    builder.append("\n\n");
    //disposal
    builder.append(res.getString(R.string.detail_vio_disposal)).append(o.getDisposal());
    builder.append("\n");
    builder.append("\n");
    return builder.toString();
  }
}
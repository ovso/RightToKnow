package io.github.ovso.righttoknow.violationfacility;

import android.os.AsyncTask;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by jaeho on 2017. 8. 1
 */

class ViolationFacilityInteractor {
  private MyAsyncTask myAsyncTask;

  public void req(String url) {
    myAsyncTask = new MyAsyncTask();
    myAsyncTask.execute(new String[] { url });
  }

  private List<ViolationFacility> getData(String source) {
    List<ViolationFacility> violationFacilities = new ArrayList<>();
    Document document;
    JSONObject rootJsonObject = new JSONObject();

    try {
      document = Jsoup.connect(source).get();
      Elements tbodyElements = document.select("tbody");
      for (Element tbodyElement : tbodyElements) {
        Elements trElements = tbodyElement.select("tr");
        for (Element trElement : trElements) {
          ViolationFacility violatioin = new ViolationFacility();
          Elements tdElements = trElement.select("td");

          String turn = tdElements.get(0).childNode(0).toString();
          String sido = tdElements.get(1).childNode(0).toString();
          String sigungu = tdElements.get(2).childNode(0).toString();
          String link = tdElements.get(3).childNode(1).attributes().get("href");
          String name = tdElements.get(3).childNode(1).childNode(0).toString();
          String type = tdElements.get(4).childNode(0).toString();
          String boss = tdElements.get(5).childNode(0).toString();
          String director = tdElements.get(6).childNode(0).toString();
          String address = tdElements.get(7).childNode(0).toString();

          violatioin.setTurn(turn);
          violatioin.setSido(sido);
          violatioin.setSigungu(sigungu);
          violatioin.setName(name);
          violatioin.setType(type);
          violatioin.setBoss(boss);
          violatioin.setDirector(director);
          violatioin.setAddress(address);
          violatioin.setLink(link);

          violationFacilities.add(violatioin);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      return violationFacilities;
    } catch (Exception e) {
      return violationFacilities;
    }

    return violationFacilities;
  }

  private class MyAsyncTask extends AsyncTask<String, Void, List<ViolationFacility>> {
    @Override protected void onPreExecute() {
      onViolationfacilityResultListener.onPre();
      super.onPreExecute();
    }

    @Override protected List<ViolationFacility> doInBackground(String... params) {
      return getData(params[0]);
    }

    private String getJson(String table) {
      JSONObject rootJsonObject = new JSONObject();

      return rootJsonObject.toString();
    }

    @Override protected void onPostExecute(List<ViolationFacility> violationFacilities) {
      super.onPostExecute(violationFacilities);
      onViolationfacilityResultListener.onResult(violationFacilities);
      onViolationfacilityResultListener.onPost();
    }
  }

  @Getter @Setter private OnViolationfacilityResultListener onViolationfacilityResultListener;

  public interface OnViolationfacilityResultListener {
    void onPre();
    void onResult(List<ViolationFacility> violationFacilities);
    void onPost();
  }
}
/*
      for (Element thead : document.select("thead")) {
        Elements e = thead.select("tr").get(0).select("th");
        for (Element element : e) {
          String a = element.childNode(0).toString();
        }
      }

 */
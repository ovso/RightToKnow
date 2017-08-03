package io.github.ovso.righttoknow.violationfacility;

import android.os.AsyncTask;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationFacilityInteractor {
  private MyAsyncTask myAsyncTask;
  public ViolationFacilityInteractor() {
    myAsyncTask = new MyAsyncTask();
  }
  public void req(String url) {
    myAsyncTask.execute(new String[] { url });
  }

  protected List<ViolationFacility> getData(String url) {
    List<ViolationFacility> violationFacilities = new ArrayList<>();
    Document document;
    try {
      document = Jsoup.connect(url).get();
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
          link = link.substring(1, link.length());
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
      onViolationFacilityResultListener.onPre();
      super.onPreExecute();
    }

    @Override protected List<ViolationFacility> doInBackground(String... params) {
      return getData(params[0]);
    }

    @Override protected void onPostExecute(List<ViolationFacility> violationFacilities) {
      super.onPostExecute(violationFacilities);
      onViolationFacilityResultListener.onResult(violationFacilities);
      onViolationFacilityResultListener.onPost();
    }
  }

  @Getter @Setter private OnViolationResultListener onViolationFacilityResultListener;

}

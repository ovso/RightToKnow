package io.github.ovso.righttoknow.violator;

import android.os.AsyncTask;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
import io.github.ovso.righttoknow.violator.vo.Violator;
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
 * Created by jaeho on 2017. 8. 3
 */

public class ViolatorInteractor {
  private MyAsyncTask myAsyncTask;

  public void req() {
    String url = "http://info.childcare.go.kr/info/cfvp/VioltactorSlL.jsp?limit=200";
    myAsyncTask = new MyAsyncTask();
    myAsyncTask.execute(new String[] { url });
  }

  protected List<Violator> getData(String url) {
    List<Violator> violators = new ArrayList<>();
    Document document;
    try {
      document = Jsoup.connect(url).get();
      Elements tbodyElements = document.select("tbody");
      for (Element tbodyElement : tbodyElements) {
        Elements trElements = tbodyElement.select("tr");
        for (Element trElement : trElements) {
          Violator violator = new Violator();
          Elements tdElements = trElement.select("td");

          String turn = tdElements.get(0).childNode(0).toString();
          String sido = tdElements.get(1).childNode(0).toString();
          String sigungu = tdElements.get(2).childNode(0).toString();
          String link = tdElements.get(3).childNode(1).attributes().get("href");
          link = link.substring(1, link.length());
          String violatorName = tdElements.get(3).childNode(1).childNode(0).toString().trim();
          String centerName = tdElements.get(4).childNode(0).toString();
          String address = tdElements.get(5).childNode(0).toString();
          String history = tdElements.get(6).childNode(0).toString();

          //violator.setTurn(turn);
          violator.setSido(sido);
          violator.setSigungu(sigungu);
          violator.setViolator(violatorName);
          //violator.setName(centerName);
          violator.setAddress(address);
          violator.setHistory(history);
          //violator.setLink(link);

          violators.add(violator);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      return violators;
    } catch (Exception e) {
      return violators;
    }

    return violators;
  }

  public void cancel() {
    myAsyncTask.cancel(true);
  }

  private class MyAsyncTask extends AsyncTask<String, Void, List<Violator>> {
    @Override protected void onPreExecute() {
      onViolationFacilityResultListener.onPre();
      super.onPreExecute();
    }

    @Override protected List<Violator> doInBackground(String... params) {
      return getData(params[0]);
    }

    @Override protected void onPostExecute(List<Violator> violators) {
      super.onPostExecute(violators);
      onViolationFacilityResultListener.onResult(violators);
      onViolationFacilityResultListener.onPost();
    }
  }

  @Getter @Setter private OnViolationResultListener onViolationFacilityResultListener;
}

package io.github.ovso.righttoknow.vfacilitydetail;

import android.os.AsyncTask;
import io.github.ovso.righttoknow.listener.OnViolationFacilityResultListener;
import io.github.ovso.righttoknow.vfacilitydetail.vo.VFacilityDetail;
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
 * Created by jaeho on 2017. 8. 2
 */

public class VFacilityDetailInteractor {
  private MyAsyncTask myAsyncTask;

  public VFacilityDetailInteractor() {
    myAsyncTask = new MyAsyncTask();
  }

  public void req(String url) {
    myAsyncTask.execute(new String[] { url });
  }

  protected List<VFacilityDetail> getData(String url) {
    List<VFacilityDetail> vFacilityDetails = new ArrayList<>();
    Document document;
    try {
      document = Jsoup.connect(url).get();
      Elements tbodyElements = document.select("tbody");
      VFacilityDetail vFacilityDetail = new VFacilityDetail();
      for (Element tbodyElement : tbodyElements) {
        Elements trElements = tbodyElement.select("tr");
        for (int i = 0; i < trElements.size(); i++) {
          Elements tdElements = trElements.get(i).select("td");
          switch (i) {
            case 0:
              String sido = tdElements.get(0).childNode(0).toString();
              String sigungu = tdElements.get(1).childNode(0).toString();
              String type = tdElements.get(2).childNode(0).toString();
              vFacilityDetail.setSido(sido);
              vFacilityDetail.setSigungu(sigungu);
              vFacilityDetail.setType(type);
              break;
            case 1:
              String nowName = tdElements.get(0).childNode(0).toString();
              String nowBoss = tdElements.get(1).childNode(0).toString();
              String nowDirector = tdElements.get(2).childNode(0).toString();
              vFacilityDetail.setName(nowName);
              vFacilityDetail.setBoss(nowBoss);
              vFacilityDetail.setDirector(nowDirector);
              break;
            case 2:
              String oldName = tdElements.get(0).childNode(0).toString();
              String oldBoss = tdElements.get(1).childNode(0).toString();
              String oldDirector = tdElements.get(2).childNode(0).toString();
              vFacilityDetail.setOldName(oldName);
              vFacilityDetail.setOldBoss(oldBoss);
              vFacilityDetail.setOldDirector(oldDirector);
              break;
            case 3:
              String address = tdElements.get(0).childNode(0).toString();
              vFacilityDetail.setAddress(address);
              break;
            case 4:
              String action = tdElements.get(0).html().replaceAll("<br>", "\n");
              vFacilityDetail.setAction(action);
              break;
            case 5:
              String disposal = tdElements.get(0).html().replaceAll("<br>", "\n");
              vFacilityDetail.setDisposal(disposal);
              break;
          }
        }
      }
      vFacilityDetails.add(vFacilityDetail);
    } catch (IOException e) {
      e.printStackTrace();
      return vFacilityDetails;
    } catch (Exception e) {
      return vFacilityDetails;
    }

    return vFacilityDetails;
  }

  private class MyAsyncTask extends AsyncTask<String, Void, List<VFacilityDetail>> {
    @Override protected void onPreExecute() {
      onViolationFacilityResultListener.onPre();
      super.onPreExecute();
    }

    @Override protected List<VFacilityDetail> doInBackground(String... params) {
      return getData(params[0]);
    }

    @Override protected void onPostExecute(List<VFacilityDetail> violationFacilities) {
      super.onPostExecute(violationFacilities);
      onViolationFacilityResultListener.onResult(violationFacilities);
      onViolationFacilityResultListener.onPost();
    }
  }

  @Getter @Setter private OnViolationFacilityResultListener onViolationFacilityResultListener;
}

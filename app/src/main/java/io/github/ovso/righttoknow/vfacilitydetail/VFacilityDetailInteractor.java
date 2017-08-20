package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.res.Resources;
import android.os.AsyncTask;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
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

  public void req(String url) {
    myAsyncTask = new MyAsyncTask();
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
              //vFacilityDetail.setName(nowName);
              //vFacilityDetail.setBoss(nowBoss);
              //vFacilityDetail.setDirector(nowDirector);
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
              vFacilityDetail.setAction(null);
              break;
            case 5:
              String disposal = tdElements.get(0).html().replaceAll("<br>", "\n");
              vFacilityDetail.setDisposal(null);
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

  public void cancel() {
    myAsyncTask.cancel(true);
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

  @Getter @Setter private OnViolationResultListener onViolationFacilityResultListener;

  protected String getResultParse(VFacilityDetail vFacilityDetail) {
    Resources res = MyApplication.getInstance().getResources();
    String sido = res.getString(R.string.detail_sido);
    String sigungu = res.getString(R.string.detail_sigungu);
    String type = res.getString(R.string.detail_type);
    String now = res.getString(R.string.detail_now);
    String daycareCenter = res.getString(R.string.detail_daycare_center);
    String boss = res.getString(R.string.detail_boss);
    String director = res.getString(R.string.detail_director);
    String vioOld = res.getString(R.string.detail_vio_old);
    String address = res.getString(R.string.detail_address);
    String vioAction = res.getString(R.string.detail_vio_action);
    String vioDisposal = res.getString(R.string.detail_vio_disposal);

    StringBuilder builder = new StringBuilder();
    builder.append(sido).append(vFacilityDetail.getSido()).append("\n\n");
    builder.append(sigungu).append(vFacilityDetail.getSigungu()).append("\n\n");
    builder.append(type).append(vFacilityDetail.getType()).append("\n\n");
    builder.append(now).append("\n");
    //builder.append(daycareCenter).append(vFacilityDetail.getName()).append("\n");
    //builder.append(boss).append(vFacilityDetail.getBoss()).append("\n");
    //builder.append(director).append(vFacilityDetail.getDirector()).append("\n\n");
    builder.append(vioOld).append("\n");
    builder.append(daycareCenter).append(vFacilityDetail.getOldName()).append("\n");
    builder.append(boss).append(vFacilityDetail.getOldBoss()).append("\n");
    builder.append(director).append(vFacilityDetail.getOldDirector()).append("\n\n");
    builder.append(address).append(vFacilityDetail.getAddress()).append("\n\n");
    builder.append(vioAction).append("\n");
    builder.append("");
    builder.append(vioDisposal).append("\n");
    builder.append("");
    return builder.toString();
  }
}

package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.res.Resources;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.vfacilitydetail.vo.VFacilityDetail;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by jaeho on 2017. 8. 4
 */

public class ViolatorDetailInteractor extends VFacilityDetailInteractor {

  @Override protected List<VFacilityDetail> getData(String url) {
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
              vFacilityDetail.setSido(sido);
              vFacilityDetail.setSigungu(sigungu);
              break;
            case 1:
              String violatorName = tdElements.get(0).childNode(0).toString();
              String centerName = tdElements.get(1).childNode(0).toString();
              vFacilityDetail.setViolatorName(violatorName);
              vFacilityDetail.setName(centerName);
              break;
            case 2:
              String history = tdElements.get(0).childNode(0).toString();
              String address = tdElements.get(1).childNode(0).toString();
              vFacilityDetail.setHistory(history);
              vFacilityDetail.setAddress(address);
              break;
            case 3:
              String action = tdElements.get(0).html().replaceAll("<br>", "\n");
              vFacilityDetail.setAction(action);
              break;
            case 4:
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

  @Override protected String getResultParse(VFacilityDetail vFacilityDetail) {
    Resources res = MyApplication.getInstance().getResources();
    String sido = res.getString(R.string.detail_sido);
    String sigungu = res.getString(R.string.detail_sigungu);
    String vioatorName = res.getString(R.string.detail_violator_name);
    String vioOldCenterName = res.getString(R.string.detail_vio_old_center_name);
    String history = res.getString(R.string.detail_violation_history);
    String action = res.getString(R.string.detail_vio_action);
    String disposal = res.getString(R.string.detail_vio_disposal);

    StringBuilder builder = new StringBuilder();
    builder.append(sido).append(vFacilityDetail.getSido()).append("\n\n");
    builder.append(sigungu).append(vFacilityDetail.getSigungu()).append("\n\n");
    builder.append(vioatorName).append(vFacilityDetail.getViolatorName()).append("\n\n");
    builder.append(vioOldCenterName).append(vFacilityDetail.getName()).append("\n\n");
    builder.append(history).append(vFacilityDetail.getHistory()).append("\n\n");
    builder.append(action).append(vFacilityDetail.getAction()).append("\n\n");
    builder.append(disposal).append(vFacilityDetail.getDisposal()).append("\n\n");
    return builder.toString();
  }
}

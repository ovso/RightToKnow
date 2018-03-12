package io.github.ovso.righttoknow.violationfacility;

import android.os.Bundle;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.violationfacility.model.ViolationFacility;
import io.github.ovso.righttoknow.violationfacility.model.ViolationFacility2;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationFacilityPresenterImpl implements ViolationFacilityPresenter {

  private ViolationFacilityPresenter.View view;
  private FacilityAdapterDataModel<ViolationFacility> adapterDataModel;
  private DatabaseReference databaseReference;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  ViolationFacilityPresenterImpl(ViolationFacilityPresenter.View view) {
    this.view = view;
    databaseReference = FirebaseDatabase.getInstance().getReference().child("child_vio_fac");
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    view.setListener();
    view.setAdapter();
    view.setRecyclerView();
    req();
  }

  private void req() {
    view.showLoading();
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .map(dataSnapshot -> {
          ViolationFacility.convertToItems(dataSnapshot);
          ArrayList<ViolationFacility> items = ViolationFacility.convertToItems(dataSnapshot);
          Comparator<ViolationFacility> comparator = (t1, t2) -> Integer.valueOf(t2.getReg_number())
              .compareTo(Integer.valueOf(t1.getReg_number()));
          Collections.sort(items, comparator);
          return items;
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          view.showMessage(R.string.error_server);
          view.hideLoading();
        }));

    Observable.fromCallable(new Callable<Object>() {
      @Override public Object call() throws Exception {
        testJsoup();

        return null;
      }
    }).subscribeOn(Schedulers.io()).subscribe(new Consumer<Object>() {
      @Override public void accept(Object o) throws Exception {

      }
    }, new Consumer<Throwable>() {
      @Override public void accept(Throwable throwable) throws Exception {
        Timber.d(throwable);
      }
    });
  }

  private void testJsoup() throws IOException, JSONException {
    List<ViolationFacility2> items = new ArrayList<>();
    Document doc =
        Jsoup.connect("http://info.childcare.go.kr/info/cfvp/VioltfcltySlL.jsp?limit=200").get();

    Elements tableElements = doc.select("tbody");
    Elements trElements = tableElements.select("tr");
    for (Element trElement : trElements) {
      Elements tdElements = trElement.select("td");
      for (int i = 0; i < tdElements.size(); i++) {
        Timber.d("value.ownText = " + tdElements.get(i).ownText());
        setItem(tdElements.get(i).ownText(), i);
      }
      Elements hrefElements = trElement.select("a[href]");
      for (int i = 0; i < hrefElements.size(); i++) {
        //o.put("link", hrefElements.get(i).attr("abs:href"));
        Timber.d("absLink = " + hrefElements.get(i).attr("abs:href"));
      }
    }

  }

  private void setItem(String text, int i) throws JSONException {
    JSONObject o = new JSONObject();
    switch (i) {
      case 0:
        o.put("order", text);
        break;
      case 1:
        o.put("sido", text);
        break;
      case 2:
        o.put("sigungu", text);
        break;
      case 3:
        break;
      case 4:
        o.put("fac_type", text);
        break;
      case 5:
        o.put("master", text);
        break;
      case 6:
        o.put("director", text);
        break;
      case 7:
        o.put("address", text);
        break;
    }
  }

  /*
      97
      대전광역시
      대덕구

      민간
      백경희
      주희경
      대전광역시 대덕구 신탄진로756번길 15 (신탄진동)
      http://info.childcare.go.kr/info/cfvp/VioltfcltySl.jsp?stcode=30230000009&violtfcltyseq=&gubun=CK&exaathrno=2015000001
   */
  @Override public void setAdapterModel(FacilityAdapterDataModel adapterDataModel) {
    this.adapterDataModel = adapterDataModel;
  }

  @Override public void onRecyclerItemClick(ViolationFacility violationFacility) {
    view.navigateToViolationFacilityDetail(violationFacility);
  }

  @Override public void onRefresh() {
    adapterDataModel.clear();
    view.refresh();
    view.setSearchResultText(R.string.empty);
    req();
  }

  @Override public void onSearchQuery(final String query) {
    view.showLoading();
    adapterDataModel.clear();
    view.refresh();
    compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
        .subscribeOn(Schedulers.io())
        .map(dataSnapshot -> {
          ArrayList<ViolationFacility> items =
              ViolationFacility.getSearchResultItems(ViolationFacility.convertToItems(dataSnapshot),
                  query);
          Comparator<ViolationFacility> comparator = (t1, t2) -> Integer.valueOf(t2.getReg_number())
              .compareTo(Integer.valueOf(t1.getReg_number()));
          Collections.sort(items, comparator);
          return items;
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(items -> {
          adapterDataModel.addAll(items);
          view.refresh();
          view.hideLoading();
        }, throwable -> {
          view.showMessage(R.string.error_server);
          view.hideLoading();
        }));
  }

  @Override public void onDetach() {
    compositeDisposable.clear();
    compositeDisposable.dispose();
  }
}
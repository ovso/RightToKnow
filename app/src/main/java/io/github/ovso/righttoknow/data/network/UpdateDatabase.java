package io.github.ovso.righttoknow.data.network;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.ovso.righttoknow.BuildConfig;
import io.github.ovso.righttoknow.framework.utils.TimeoutMillis;
import io.github.ovso.righttoknow.ui.main.violationfacility.model.VioFac;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import timber.log.Timber;

public class UpdateDatabase {
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private final static String URL_VIOLATION =
      "http://info.childcare.go.kr/info/cfvp/VioltfcltySlL.jsp?limit=500";
  private final static String URL_VIOLATORS =
      "http://info.childcare.go.kr/info/cfvp/VioltactorSlL.jsp?limit=500";
  private final static String URL_CERTIFIED =
      "http://info.childcare.go.kr/info/cera/community/notice/CertNoticeSlPL.jsp?limit=500";
  private SchedulersFacade schedulersFacade = new SchedulersFacade();

  public void update() {
    if (BuildConfig.DEBUG) {
      reqViolation(URL_VIOLATION);
      reqViolators(URL_VIOLATORS);
      reqCertified(URL_CERTIFIED);
    }
  }

  private void reqCertified(String url) {
  }

  private void reqViolators(String url) {

  }

  private Document getDoc(String url) throws Exception {
    return Jsoup.connect(URL_VIOLATION).timeout(TimeoutMillis.JSOUP.getValue()).get();
  }

  private void reqViolation(String url) {
    Single.fromCallable(() -> VioFac.toJson(getDoc(url)))
        .subscribeOn(schedulersFacade.io())
        .subscribe(new SingleObserver<JSONArray>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(JSONArray $jsonArray) {
            /*
            int length = $jsonArray.length();
            JsonArray jsonElements = new Gson().fromJson($jsonArray.toString(), JsonArray.class);
            List<Single<JsonObject>> singles = new ArrayList<>();
            for (JsonElement jsonElement : jsonElements) {
              JsonObject jsonObject = jsonElement.getAsJsonObject();
              String link = jsonObject.get("link").toString();

              Single<JsonObject> detail =
                  Single.fromCallable(() -> VioFac.toDetailJson(getDoc(link))
                  ).map($detailJsonObject -> {
                    jsonObject.add("detail", $detailJsonObject);
                    return jsonObject;
                  });
              singles.add(detail);
            }

            List<Single<JSONObject>> singles = new ArrayList<>();
            for (int i = 0; i < length; i++) {
              try {
                JSONObject jsonObject = $jsonArray.getJSONObject(i);
                String link = jsonObject.optString("link");

                Single<JSONObject> detail =
                    Single.fromCallable(() -> VioFac.toDetailJson(getDoc(link))
                    ).map($detailJsonObject -> {
                      jsonObject.put("detail", $detailJsonObject);
                      return jsonObject;
                    });
                singles.add(detail);
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }

            Disposable subscribe =
                Single.concat(singles).subscribeOn(schedulersFacade.io()).subscribe(jsonObject -> {
                  Timber.d("jsonObject = " + jsonObject);
                });

            compositeDisposable.add(subscribe);
            */
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
          }
        });
  }
}

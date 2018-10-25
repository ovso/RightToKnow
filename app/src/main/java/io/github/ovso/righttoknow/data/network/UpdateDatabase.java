package io.github.ovso.righttoknow.data.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.ovso.righttoknow.BuildConfig;
import io.github.ovso.righttoknow.data.DocumentParse;
import io.github.ovso.righttoknow.framework.utils.TimeoutMillis;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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

  private void reqViolation(String url) {

    Single.fromCallable(() -> DocumentParse.violation(getDoc(url)))
        .subscribeOn(schedulersFacade.io())
        .subscribe(new SingleObserver<JsonArray>() {
          @Override public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
          }

          @Override public void onSuccess(JsonArray $jsonArray) {
            List<Observable<JsonObject>> observables = new ArrayList<>();
            for (JsonElement jsonElement : $jsonArray) {
              JsonObject $jsonObject = jsonElement.getAsJsonObject();
              String link = $jsonObject.get("link").toString();

              Observable<JsonObject> detail =
                  Observable.fromCallable(() -> DocumentParse.violationDetail(getDoc(link))
                  ).map($detailJsonObject -> {
                    $jsonObject.add("detail", $detailJsonObject);
                    return $jsonObject;
                  });
              observables.add(detail);
            }

            final JsonArray jsonArray = new JsonArray();
            AtomicInteger atomicInteger = new AtomicInteger(0);
            Observable.concat(observables).subscribeOn(schedulersFacade.io()).subscribe(
                new Observer<JsonObject>() {
                  @Override public void onSubscribe(Disposable d) {

                  }

                  @Override public void onNext(JsonObject $jsonObject) {
                    jsonArray.add($jsonObject);
                  }

                  @Override public void onError(Throwable e) {

                  }

                  @Override public void onComplete() {
                    Timber.d("onComplete()");
                  }
                });
          }

          @Override public void onError(Throwable e) {
            Timber.d(e);
          }
        });
  }

  private void reqCertified(String url) {
  }

  private void reqViolators(String url) {

  }

  private Document getDoc(String url) throws Exception {
    return Jsoup.connect(URL_VIOLATION).timeout(TimeoutMillis.JSOUP.getValue()).get();
  }
}

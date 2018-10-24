package io.github.ovso.righttoknow;

import android.os.HandlerThread;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.github.ovso.righttoknow.framework.di.DaggerAppComponent;
import io.github.ovso.righttoknow.framework.utils.AppInitUtils;
import io.github.ovso.righttoknow.framework.utils.MessagingHandler;

public class App extends DaggerApplication {
  private final static String violator =
      "http://info.childcare.go.kr/info/cfvp/VioltfcltySlL.jsp?limit=500";
  private final static String URL2 =
      "http://info.childcare.go.kr/info/cfvp/VioltfcltySlL.jsp?limit=500";
  public static boolean DEBUG = false;
  private static App instance;

  public static App getInstance() {
    return instance;
  }

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    DEBUG = AppInitUtils.debug(getApplicationContext());
    AppInitUtils.ad(getApplicationContext());
    AppInitUtils.timber(DEBUG);
    AppInitUtils.prDownloader(getApplicationContext());
    MessagingHandler.createChannelToShowNotifications();
  }

  /*
  private void reqSecond() {
    Single.fromCallable(() -> {
      Document document = Jsoup.connect(connectUrl).timeout(TimeoutMillis.JSOUP.getValue()).get();
      return VioFac.toJson(document);
    }).subscribeOn(schedulersFacade.io()).subscribe(new SingleObserver<JSONArray>() {
      @Override public void onSubscribe(Disposable d) {
        compositeDisposable.add(d);
      }

      @Override public void onSuccess(JSONArray $jsonArray) {
        int length = $jsonArray.length();
        List<Single<JSONObject>> singles = new ArrayList<>();
        for (int i = 0; i < length; i++) {
          try {
            JSONObject jsonObject = $jsonArray.getJSONObject(i);
            String link = jsonObject.optString("link");

            Single<JSONObject> detail = Single.fromCallable(() -> {
                  Document document = Jsoup.connect(link).timeout(TimeoutMillis.JSOUP.getValue()).get();
                  JSONObject detailJsonObject = VioFac.toDetailJson(document);
                  return detailJsonObject;
                }
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
      }

      @Override public void onError(Throwable e) {
        Timber.d(e);
      }
    });
  }
  */

  @Override protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerAppComponent.builder().application(this).build();
  }
}
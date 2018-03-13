package io.github.ovso.righttoknow.vfacilitydetail;

import android.content.Intent;
import android.os.Bundle;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.vfacilitydetail.model.VioFacDe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import org.jsoup.Jsoup;
import timber.log.Timber;

/**
 * Created by jaeho on 2017. 8. 2
 */

public class VFacilityDetailPresenterImpl implements VFacilityDetailPresenter {
  private VFacilityDetailPresenter.View view;
  private VFacilityDetailModel model;

  VFacilityDetailPresenterImpl(VFacilityDetailPresenter.View view) {
    this.view = view;
    model = new VFacilityDetailModel(MyApplication.getInstance());
  }

  @Override public void onCreate(Bundle savedInstanceState, Intent intent) {
    view.setListener();
    view.setSupportActionBar();
    if (intent.hasExtra("link")) {
      final String link = intent.getStringExtra("link");
      view.setTitle("위반시설 조회 상세");
      Timber.d("link = " + link);
      Observable.fromCallable(new Callable<String>() {
        @Override public String call() throws Exception {
          return VioFacDe.getFacContents(VioFacDe.convertToItem(Jsoup.connect(link).get()));
        }
      })
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Consumer<String>() {
            @Override public void accept(String o) throws Exception {
              view.showContents(o);
            }
          }, new Consumer<Throwable>() {
            @Override public void accept(Throwable throwable) throws Exception {
              Timber.d(throwable);
            }
          });
    }

    view.showAd();
  }

  @Override public void onDestroy() {
  }

  @Override public void onBackPressed() {
  }
}
package io.github.ovso.righttoknow.pdfviewer;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
import java.io.File;

/**
 * Created by jaeho on 2017. 8. 22
 */

public class PDFViewerPresenterImpl implements PDFViewerPresenter {

  private PDFViewerPresenter.View view;
  private PDFViewerInteractor interactor;

  PDFViewerPresenterImpl(PDFViewerPresenter.View view) {
    this.view = view;
    interactor = new PDFViewerInteractor();
    interactor.setOnChildResultListener(new OnChildResultListener<File>() {
      @DebugLog @Override public void onPre() {
        view.showLoading();
      }

      @DebugLog @Override public void onResult(File result) {
        view.showPDF(result);
      }

      @DebugLog @Override public void onPost() {
        view.hideLoading();
      }

      @DebugLog @Override public void onError() {
        view.hideLoading();
      }
    });
  }

  @Override public void onCreate(Bundle savedInstanceState, Intent intent) {
    view.setProgressbarColor(R.color.colorPrimary);
    if (intent.hasExtra("name")) {
      String fileName = intent.getStringExtra("name");
      view.setTitle(getTitle(fileName));
      interactor.setFileName(fileName);
      interactor.req();
    }
  }

  private String getTitle(String fileName) {
    Resources res = MyApplication.getInstance().getResources();
    if (!TextUtils.isEmpty(fileName)) {
      String year = fileName.substring(0, 4);
      String month = fileName.substring(4, 6);
      String title = year
          + res.getString(R.string.year)
          + " "
          + month
          + res.getString(R.string.month)
          + " "
          + res.getString(R.string.title_child_certified_detail);
      return title;
    } else {
      return res.getString(R.string.title_child_certified);
    }
  }
}

package io.github.ovso.righttoknow.pdfviewer;

import android.content.Intent;
import android.os.Bundle;
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
    if (intent.hasExtra("name")) {
      interactor.setFileName(intent.getStringExtra("name"));
      interactor.req();
    }

    view.setTitle(MyApplication.getInstance().getString(R.string.title_child_certified));
  }
}

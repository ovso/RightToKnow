package io.github.ovso.righttoknow.pdfviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import java.io.File;

/**
 * Created by jaeho on 2017. 8. 22
 */

public interface PDFViewerPresenter {

  void onCreate(Bundle savedInstanceState, Intent intent);

  void onBackPressed();

  void onOptionsItemSelected();

  public interface View {

    void showLoading();

    void hideLoading();

    void showPDF(File file);

    void setTitle(String title);

    void setProgressbarColor(@ColorRes int color);

    void finishActivity();
  }
}

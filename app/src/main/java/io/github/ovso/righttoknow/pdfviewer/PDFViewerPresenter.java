package io.github.ovso.righttoknow.pdfviewer;

import android.content.Intent;
import android.os.Bundle;
import java.io.File;

/**
 * Created by jaeho on 2017. 8. 22
 */

public interface PDFViewerPresenter {

  void onCreate(Bundle savedInstanceState, Intent intent);

  public interface View {

    void showLoading();

    void hideLoading();

    void showPDF(File file);
  }
}

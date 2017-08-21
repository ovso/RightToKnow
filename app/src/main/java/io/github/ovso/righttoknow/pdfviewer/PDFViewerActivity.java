package io.github.ovso.righttoknow.pdfviewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.BindView;
import com.github.barteksc.pdfviewer.PDFView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.main.BaseActivity;
import java.io.File;

/**
 * Created by jaeho on 2017. 8. 21
 */

public class PDFViewerActivity extends BaseActivity implements PDFViewerPresenter.View {
  @BindView(R.id.pdf_view) PDFView pdfView;
  private PDFViewerPresenter presenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = new PDFViewerPresenterImpl(this);
    presenter.onCreate(savedInstanceState, getIntent());
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_pdfviewer;
  }

  @Override public void showLoading() {

  }

  @Override public void hideLoading() {

  }

  @Override public void showPDF(File file) {
    pdfView.fromFile(file).load();
  }
}
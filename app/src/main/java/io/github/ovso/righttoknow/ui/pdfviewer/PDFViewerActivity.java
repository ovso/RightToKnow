package io.github.ovso.righttoknow.ui.pdfviewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import butterknife.BindView;
import com.github.barteksc.pdfviewer.PDFView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.AdsActivity;
import java.io.File;

public class PDFViewerActivity extends AdsActivity {
  @BindView(R.id.pdf_view) PDFView pdfView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(R.string.title_child_certified_detail);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    if (getIntent().hasExtra("file")) {
      File file = (File) getIntent().getSerializableExtra("file");
      pdfView.fromFile(file).load();
    }
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_pdfviewer;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return true;
  }
}
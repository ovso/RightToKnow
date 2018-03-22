package io.github.ovso.righttoknow.pdfviewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import butterknife.BindView;
import com.github.barteksc.pdfviewer.PDFView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.main.BaseActivity;
import java.io.File;

/**
 * Created by jaeho on 2017. 8. 21
 */

public class PDFViewerActivity extends BaseActivity {
  @BindView(R.id.pdf_view) PDFView pdfView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getIntent().hasExtra("file")) {
      File file = (File) getIntent().getSerializableExtra("file");
      pdfView.fromFile(file).load();
    }

    setTitle(R.string.title_child_certified_detail);
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_pdfviewer;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return true;
  }
}
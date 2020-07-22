package io.github.ovso.righttoknow.ui.pdfviewer

import android.os.Bundle
import android.view.MenuItem
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.databinding.ActivityPdfviewerBinding
import io.github.ovso.righttoknow.exts.viewBinding
import io.github.ovso.righttoknow.framework.AdsActivity
import kotlinx.android.synthetic.main.content_pdf_viewer.*
import java.io.File

class PDFViewerActivity : AdsActivity() {
  private val binding by viewBinding(ActivityPdfviewerBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setTitle(R.string.title_child_certified_detail)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    if (intent.hasExtra("file")) {
      val file = intent.getSerializableExtra("file") as File
      pdf_view.fromFile(file).load()
    }
    showAd()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    finish()
    return true
  }
}

package io.github.ovso.righttoknow.ui.pdfviewer

import android.os.Bundle
import android.view.MenuItem
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.databinding.ActivityPdfviewerBinding
import io.github.ovso.righttoknow.exts.viewBinding
import io.github.ovso.righttoknow.framework.AdsActivity
import java.io.File

class PDFViewerActivity : AdsActivity() {
  private val binding by viewBinding(ActivityPdfviewerBinding::inflate)
  private val pdfView = binding.includePdfviewerContainer.includePdfviewerConentsContainer.pdfView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setTitle(R.string.title_child_certified_detail)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    if (intent.hasExtra("file")) {
      val file = intent.getSerializableExtra("file") as File
      pdfView.fromFile(file).load()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    finish()
    return true
  }
}
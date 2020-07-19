package io.github.ovso.righttoknow.ui.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.databinding.ActivitySettingsBinding
import io.github.ovso.righttoknow.exts.viewBinding
import io.github.ovso.righttoknow.framework.BaseActivity

class SettingsActivity : BaseActivity() {
  private val binding by viewBinding(ActivitySettingsBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }

  override fun setSupportActionBar(toolbar: Toolbar?) {
    super.setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setTitle(R.string.action_oss)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    finish()
    return true
  }
}

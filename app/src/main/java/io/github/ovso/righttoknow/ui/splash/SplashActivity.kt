package io.github.ovso.righttoknow.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.ovso.righttoknow.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    startActivity(Intent(this, MainActivity::class.java))
    finish()
  }
}

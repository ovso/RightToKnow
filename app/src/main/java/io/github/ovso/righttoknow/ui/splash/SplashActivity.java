package io.github.ovso.righttoknow.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.network.UpdateDatabase;
import io.github.ovso.righttoknow.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //navigateToMain();
    setContentView(R.layout.activity_splash);
    UpdateDatabase updateDatabase = new UpdateDatabase();
    updateDatabase.setCompleteListener(() -> navigateToMain());
    updateDatabase.update();
  }

  private void navigateToMain() {
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }
}
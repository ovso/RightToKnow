package io.github.ovso.righttoknow.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.common.Utility;
import io.github.ovso.righttoknow.main.BaseActivity;
import io.github.ovso.righttoknow.main.MainActivity;

/**
 * Created by jaeho on 2017. 8. 14
 */

public class SplashActivity extends BaseActivity {
  @BindView(R.id.progressbar) ProgressBar progressBar;

  private Handler handler = new Handler();
  private Runnable runnable = () -> {
    progressBar.setVisibility(View.INVISIBLE);
    if (Utility.isOnline(getApplicationContext())) {
      navigateToMain();
    } else {
      new AlertDialog.Builder(this).setMessage(R.string.not_connected_internet)
          .setCancelable(false)
          .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> finish())
          .show();
    }
  };

  private void navigateToMain() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    finish();
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    handler.postDelayed(runnable, 2500);

    progressBar.getIndeterminateDrawable()
        .setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary),
            android.graphics.PorterDuff.Mode.MULTIPLY);

    Utility.setStatusBarColor(this,
        ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_splash;
  }

  @Override public void onBackPressed() {
    handler.removeCallbacks(runnable);
    super.onBackPressed();
  }
}

package io.github.ovso.righttoknow.framework;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import dagger.android.AndroidInjection;
import io.github.ovso.righttoknow.R;

/**
 * Created by jaeho on 2017. 7. 31
 */

public abstract class BaseActivity extends AppCompatActivity {
  private Unbinder unbinder;
  protected @BindView(R.id.toolbar) Toolbar toolbar;
  protected @BindView(R.id.search_view) MaterialSearchView searchView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AndroidInjection.inject(this);
    setContentView(getLayoutResId());
    unbinder = ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    setNavigationBarColor();
  }

  private void setNavigationBarColor() {
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }
  }

  @LayoutRes protected abstract int getLayoutResId();

  @Override protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  protected Toolbar getToolbar() {
    return toolbar;
  }
}

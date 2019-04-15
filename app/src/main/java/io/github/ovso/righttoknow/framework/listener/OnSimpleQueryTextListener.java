package io.github.ovso.righttoknow.framework.listener;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

/**
 * Created by jaeho on 2018. 2. 4..
 */

public abstract class OnSimpleQueryTextListener implements MaterialSearchView.OnQueryTextListener {

  @Override public boolean onQueryTextChange(String newText) {
    return false;
  }

  @Override public boolean onQueryTextSubmit(String query) {
    onSubmit(query);
    return false;
  }

  public abstract void onSubmit(String query);
}

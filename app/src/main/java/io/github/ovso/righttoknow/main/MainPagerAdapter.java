package io.github.ovso.righttoknow.main;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import io.github.ovso.righttoknow.fragment.BaseFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class MainPagerAdapter extends FragmentPagerAdapter
    implements MainAdapterView, MainAdapterDataModel<BaseFragment> {
  private ArrayList<BaseFragment> items = new ArrayList<>();

  public MainPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public void add(BaseFragment item) {
    items.add(item);
  }

  @Override public void addAll(List<BaseFragment> items) {
    this.items.addAll(items);
  }

  @Override public BaseFragment remove(int position) {
    return items.remove(position);
  }

  @Override public BaseFragment getItem(int position) {
    return items.get(position);
  }

  @Override public int getSize() {
    return items.size();
  }

  @Override public int getCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }
}
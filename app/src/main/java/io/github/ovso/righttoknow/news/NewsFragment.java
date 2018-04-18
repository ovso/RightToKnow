package io.github.ovso.righttoknow.news;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.framework.BaseFragment;
import io.github.ovso.righttoknow.news.model.News;
import io.github.ovso.righttoknow.newsdetail.DetailNewsActivity;

/**
 * Created by jaeho on 2017. 9. 1
 */

public class NewsFragment extends BaseFragment implements NewsFragmentPresenter.View {
  public static NewsFragment newInstance() {
    NewsFragment f = new NewsFragment();
    return f;
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_news;
  }

  private NewsFragmentPresenter presenter;

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);
    presenter = new NewsFragmentPresenterImpl(this);
    presenter.onActivityCreated(savedInstanceState);
  }

  @BindView(R.id.recyclerview) RecyclerView recyclerView;

  @Override public void setRecyclerView() {
    LinearLayoutManager layout = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layout);
    recyclerView.setAdapter(adapter);
  }

  @Override public void setListener() {
    swipeRefresh.setOnRefreshListener(() -> {
      presenter.onRefresh();
    });
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
  }

  private BaseAdapterView adapterView;
  private NewsAdapter adapter;

  @Override public void setAdapter() {
    adapter = new NewsAdapter();
    presenter.setAdapterModel(adapter);
    adapterView = adapter;
  }

  @Override public void refresh() {
    adapterView.refresh();
  }

  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;

  @Override public void showLoading() {
    swipeRefresh.setRefreshing(true);
  }

  @Override public void hideLoading() {
    if (swipeRefresh != null) {
      swipeRefresh.setRefreshing(false);
    }
  }

  @Override public void navigateToDetailNews(News item) {
    Intent intent = new Intent(getContext(), DetailNewsActivity.class);
    intent.putExtra("news", item);
    startActivity(intent);
  }

  @Override public void showSimpleNewsDialog(News item) {
    SpannableString title = new SpannableString(Html.fromHtml(item.getTitle()));
    SpannableString contents = new SpannableString(Html.fromHtml(item.getDescription()));

    new AlertDialog.Builder(getActivity()).setTitle(title)
        .setMessage(contents)
        .setPositiveButton(android.R.string.ok, null)
        .show();
  }

  @Override public void showMessage(int resId) {
    Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
  }

  @Override public void onDetach() {
    super.onDetach();
    presenter.onDetach();
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    menu.findItem(R.id.option_menu_search).setVisible(false);
    menu.findItem(R.id.option_menu_sort).setVisible(false);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override public void onResume() {
    super.onResume();
    getActivity().setTitle(R.string.title_news);
  }
}

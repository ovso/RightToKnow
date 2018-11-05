package io.github.ovso.righttoknow.ui.main.violator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.App;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.data.network.model.certified.VioDataWrapper;
import io.github.ovso.righttoknow.data.network.model.violators.Violator;
import io.github.ovso.righttoknow.framework.BaseFragment;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.framework.listener.OnFragmentEventListener;
import io.github.ovso.righttoknow.ui.violator_contents.ViolatorContentsActivity;
import io.github.ovso.righttoknow.utils.ResourceProvider;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import org.parceler.Parcels;

public class ViolatorFragment extends BaseFragment
    implements ViolatorFragmentPresenter.View, OnFragmentEventListener {
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  @BindView(R.id.search_result_textview) TextView searchResultTextView;
  @BindView(R.id.container_view) View containerView;

  private ViolatorAdapter adapter = new ViolatorAdapter();
  private BaseAdapterView adapterView;
  private ViolatorFragmentPresenter presenter;

  public static ViolatorFragment newInstance() {
    ViolatorFragment f = new ViolatorFragment();
    return f;
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_violator;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);
    presenter = createPresenter();
    presenter.onActivityCreate(savedInstanceState);
  }

  private ViolatorFragmentPresenter createPresenter() {
    BaseAdapterDataModel<Violator> adapterDataModel = adapter;
    adapterView = adapter;
    SchedulersFacade schedulersFacade = new SchedulersFacade();
    ResourceProvider resourceProvider = new ResourceProvider(getContext());
    VioDataWrapper vioDataWrapper = ((App) getActivity().getApplication()).getVioDataWrapper();
    ViolatorFragmentPresenter p = new ViolatorFragmentPresenterImpl(
        this,
        schedulersFacade,
        resourceProvider,
        adapterDataModel,
        vioDataWrapper.vioData
    );
    getLifecycle().addObserver(p);
    return p;
  }

  @Override public void hideLoading() {
    swipeRefresh.setRefreshing(false);
  }

  @Override public void showLoading() {
    swipeRefresh.setRefreshing(true);
  }

  @Override public void refresh() {
    adapterView.refresh();
  }

  @Override public void setupAdapter() {
    adapter.setOnRecyclerItemClickListener(violator -> presenter.onRecyclerItemClick(violator));
  }

  @Override public void setupRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
  }

  @Override public void navigateToViolatorDetail(Violator violator) {
    Intent intent = new Intent(getContext(), ViolatorContentsActivity.class);
    intent.putExtra("contents", Parcels.wrap(violator.contents));
    startActivity(intent);
  }

  @Override public void setListener() {
    swipeRefresh.setOnRefreshListener(() -> presenter.onRefresh());
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    setHasOptionsMenu(true);
  }

  @Override public void setSearchResultText(@StringRes int resId) {
    searchResultTextView.setText(resId);
  }

  @Override public void showMessage(int resId) {
    Snackbar.make(containerView, resId, Snackbar.LENGTH_SHORT).show();
  }

  @Override public void onSearchQuery(String query) {
    presenter.onSearchQuery(query);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    //return super.onOptionsItemSelected(item);
    presenter.onOptionsItemSelected(item.getItemId());
    return false;
  }

  @Override public void onResume() {
    super.onResume();
    getActivity().setTitle(R.string.title_violator_inquiry);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    menu.findItem(R.id.option_menu_search).setVisible(false);
    super.onCreateOptionsMenu(menu, inflater);
  }
}
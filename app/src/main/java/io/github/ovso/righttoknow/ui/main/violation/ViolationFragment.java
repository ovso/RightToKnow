package io.github.ovso.righttoknow.ui.main.violation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import io.github.ovso.righttoknow.data.network.model.violation.Violation;
import io.github.ovso.righttoknow.framework.BaseFragment;
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterView;
import io.github.ovso.righttoknow.framework.listener.OnFragmentEventListener;
import io.github.ovso.righttoknow.ui.violation_contents.ViolationContentsActivity;
import io.github.ovso.righttoknow.utils.ResourceProvider;
import io.github.ovso.righttoknow.utils.SchedulersFacade;
import org.parceler.Parcels;

public class ViolationFragment extends BaseFragment
    implements ViolationFragmentPresenter.View, OnFragmentEventListener {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;

  private ViolationAdapter adapter = new ViolationAdapter();
  private BaseAdapterView adapterView;
  private ViolationFragmentPresenter presenter;

  public static ViolationFragment newInstance() {
    ViolationFragment f = new ViolationFragment();
    return f;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);
    presenter = createPresenter();
    presenter.onActivityCreated(savedInstanceState);
  }

  private ViolationFragmentPresenter createPresenter() {
    ViolationFragmentPresenter.View view = this;
    SchedulersFacade schedulersFacade = new SchedulersFacade();
    ResourceProvider rProvider = new ResourceProvider(getContext());
    VioDataWrapper vioDataWrapper = ((App) getActivity().getApplication()).getVioDataWrapper();
    return new ViolationFragmentPresenterImpl(
        view,
        schedulersFacade,
        rProvider,
        vioDataWrapper.vioData
    );
  }

  @Override public int getLayoutResId() {
    return R.layout.fragment_violation;
  }

  @Override public void setRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
  }

  @Override public void setAdapter() {
    presenter.setAdapterModel(adapter);
    adapterView = adapter;
    adapter.setOnRecyclerItemClickListener(
        violationFacility -> presenter.onRecyclerItemClick(violationFacility));
  }

  @Override public void refresh() {
    adapterView.refresh();
  }

  @Override public void showLoading() {
    swipeRefresh.setRefreshing(true);
  }

  @Override public void hideLoading() {
    swipeRefresh.setRefreshing(false);
  }

  @Override public void navigateToContents(Violation violation) {
    Intent intent = new Intent(getContext(), ViolationContentsActivity.class);
    intent.putExtra("contents", Parcels.wrap(violation.contents));
    startActivity(intent);
  }

  @Override public void setListener() {
    swipeRefresh.setOnRefreshListener(() -> presenter.onRefresh());
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    setHasOptionsMenu(true);
  }

  @BindView(R.id.search_result_textview) TextView searchResultTextView;

  @Override public void setSearchResultText(int resId) {
    searchResultTextView.setText(resId);
  }

  @Override public void showMessage(int resId) {
    Snackbar.make(containerView, resId, Snackbar.LENGTH_SHORT).show();
  }

  @BindView(R.id.container_view) View containerView;

  @Override public void onDestroyView() {
    presenter.onDestroyView();
    super.onDestroyView();
  }

  @Override public void onSearchQuery(String query) {
    presenter.onSearchQuery(query);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    presenter.onOptionsItemSelected(item.getItemId());
    return super.onOptionsItemSelected(item);
  }

  @Override public void onResume() {
    super.onResume();
    getActivity().setTitle(R.string.title_vioation_facility_inquiry);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    menu.findItem(R.id.option_menu_search).setVisible(false);
    super.onCreateOptionsMenu(menu, inflater);
  }
}
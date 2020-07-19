package io.github.ovso.righttoknow.ui.main.violator

import android.os.Bundle
import android.text.TextUtils
import io.github.ovso.righttoknow.App.Companion.instance
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.utils.Constants
import io.github.ovso.righttoknow.framework.utils.TimeoutMillis
import io.github.ovso.righttoknow.ui.main.violationfacility.Sido
import io.github.ovso.righttoknow.ui.main.violator.model.Violator
import io.github.ovso.righttoknow.ui.main.violator.model.Violator.Companion.convertToItems
import io.github.ovso.righttoknow.ui.main.violator.model.Violator.Companion.searchResultItems
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import timber.log.Timber

class ViolatorFragmentPresenterImpl internal constructor(private val view: ViolatorFragmentPresenter.View) :
  ViolatorFragmentPresenter {
  private lateinit var adapterDataModel: ViolatorAdapterDataModel<Violator>
  private val compositeDisposable = CompositeDisposable()
  private val connectUrl =
    Constants.BASE_URL + Constants.VIOLATOR_LIST_PATH_QUERY

  override fun onActivityCreate(savedInstanceState: Bundle?) {
    view.showLoading()
    view.setListener()
    view.setAdapter()
    view.setRecyclerView()
    req()
  }

  private fun req() {
    compositeDisposable.add(
      Observable.fromCallable {
        convertToItems(
          Jsoup.connect(connectUrl).timeout(TimeoutMillis.TIME.value).get()
        )
      }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ items: List<Violator>? ->
          adapterDataModel.addAll(items!!)
          view.refresh()
          view.hideLoading()
        }) { throwable: Throwable? ->
          Timber.d(throwable)
          view.showMessage(R.string.error_server)
          view.hideLoading()
        }
    )
  }

  override fun setAdapterModel(adapterDataModel: ViolatorAdapterDataModel<Violator>) {
    this.adapterDataModel = adapterDataModel
  }

  override fun onRecyclerItemClick(violator: Violator) {
    view.navigateToViolatorDetail(violator.link, violator.address)
  }

  override fun onDestroyView() {
    compositeDisposable.clear()
  }

  override fun onRefresh() {
    adapterDataModel!!.clear()
    view.refresh()
    view.setSearchResultText(R.string.empty)
    req()
  }

  override fun onSearchQuery(query: String) {
    view.showLoading()
    adapterDataModel!!.clear()
    view.refresh()
    compositeDisposable.add(Observable.fromCallable {
      val items = convertToItems(
        Jsoup.connect(connectUrl).timeout(TimeoutMillis.TIME.value).get()
      )
      searchResultItems(query, items)
    }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
      { items: List<Violator>? ->
        adapterDataModel!!.addAll(items!!)
        view.refresh()
        view.hideLoading()
      }
    ) { throwable: Throwable? ->
      Timber.d(throwable)
      view.hideLoading()
    }
    )
  }

  override fun onOptionsItemSelected(itemId: Int) {
    val sido = Sido.getSido(itemId, instance)
    if (!TextUtils.isEmpty(sido)) {
      onSearchQuery(sido)
    }
  }

}
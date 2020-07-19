package io.github.ovso.righttoknow.ui.main.violationfacility

import android.os.Bundle
import android.text.TextUtils
import io.github.ovso.righttoknow.App.Companion.instance
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel
import io.github.ovso.righttoknow.framework.utils.Constants
import io.github.ovso.righttoknow.framework.utils.TimeoutMillis
import io.github.ovso.righttoknow.ui.main.violationfacility.model.VioFac
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import timber.log.Timber
import java.util.*

class ViolationFacilityPresenterImpl internal constructor(private val view: ViolationFacilityPresenter.View) :
  ViolationFacilityPresenter {
  private var adapterDataModel: BaseAdapterDataModel<VioFac>? = null
  private val compositeDisposable = CompositeDisposable()
  private val connectUrl by lazy {
    Constants.BASE_URL + Constants.FAC_LIST_PATH_QUERY
  }
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    view.setListener()
    view.setAdapter()
    view.setRecyclerView()
    req()
  }

  private fun req() {
    view.showLoading()
    compositeDisposable.add(Maybe.fromCallable {
      VioFac.convertToItems(
        Jsoup.connect(connectUrl).timeout(TimeoutMillis.TIME.value).get()
      )
    }
      .onErrorReturn { ArrayList() }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({ items: List<VioFac> ->
        adapterDataModel!!.addAll(items)
        view.refresh()
        if (items.isEmpty()) {
          view.showMessage(R.string.error_server)
        }
        view.hideLoading()
      }) { throwable: Throwable? ->
        Timber.e(throwable)
        view.showMessage(R.string.error_server)
        view.hideLoading()
      }
    )
  }

  override fun setAdapterModel(adapterDataModel: BaseAdapterDataModel<VioFac>) {
    this.adapterDataModel = adapterDataModel
  }

  override fun onRecyclerItemClick(vioFac: VioFac) {
    val webLink: String = vioFac.link
    val address: String = vioFac.address
    view.navigateToViolationFacilityDetail(webLink, address)
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
      val items = VioFac.convertToItems(
        Jsoup.connect(connectUrl).timeout(TimeoutMillis.TIME.value).get()
      )
      VioFac.searchResultItems(query, items)
    }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
      { items: List<VioFac>? ->
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

  override fun onDestroyView() {
    compositeDisposable.clear()
  }
}
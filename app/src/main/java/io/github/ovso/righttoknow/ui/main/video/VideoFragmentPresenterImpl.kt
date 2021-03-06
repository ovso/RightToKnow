package io.github.ovso.righttoknow.ui.main.video

import android.content.Intent
import android.text.TextUtils
import com.google.android.gms.ads.InterstitialAd
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.data.ActivityReqCode
import io.github.ovso.righttoknow.data.network.VideoRequest
import io.github.ovso.righttoknow.data.network.model.video.SearchItem
import io.github.ovso.righttoknow.framework.adapter.BaseAdapterDataModel
import io.github.ovso.righttoknow.utils.ResourceProvider
import io.github.ovso.righttoknow.utils.SchedulersFacade
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class VideoFragmentPresenterImpl internal constructor(
  private val view: VideoFragmentPresenter.View, private val interstitialAd: InterstitialAd,
  private val videoRequest: VideoRequest, private val resourceProvider: ResourceProvider,
  private val schedulersFacade: SchedulersFacade
) : VideoFragmentPresenter {
  private var adapterDataModel: BaseAdapterDataModel<SearchItem>? = null
  private val compositeDisposable = CompositeDisposable()
  private var pageToken: String? = null
  private var q: String? = null
  override fun onActivityCreated() {
    view.setRefreshLayout()
    view.setRecyclerView()
    req()
  }

  private fun req() {
    view.showLoading()
    q = resourceProvider.getString(R.string.video_query)
    val disposable = videoRequest.getResult(q!!, pageToken)
      .subscribeOn(schedulersFacade.io())
      .observeOn(schedulersFacade.ui())
      .subscribe({ (_, _, nextPageToken, _, _, items) ->
        pageToken = nextPageToken
        adapterDataModel!!.addAll(items)
        view.refresh()
        view.hideLoading()
      }) { throwable: Throwable? -> view.hideLoading() }
    compositeDisposable.add(disposable)
  }

  override fun setAdapterDataModel(dataModel: BaseAdapterDataModel<SearchItem>) {
    adapterDataModel = dataModel
  }

  override fun onRefresh() {
    adapterDataModel!!.clear()
    view.refresh()
    pageToken = null
    req()
  }

  override fun onDestroyView() {
    compositeDisposable.clear()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    if (requestCode == ActivityReqCode.YOUTUBE.get() && interstitialAd.isLoaded) {
      interstitialAd.show()
    }
  }

  override fun onLoadMore() {
    if (!TextUtils.isEmpty(pageToken) && !TextUtils.isEmpty(q)) {
      val disposable = videoRequest.getResult(q!!, pageToken!!)
        .subscribeOn(schedulersFacade.io())
        .observeOn(schedulersFacade.ui())
        .subscribe(
          { (_, _, nextPageToken, _, _, items) ->
            pageToken = nextPageToken
            adapterDataModel!!.addAll(items)
            view.refresh()
            view.setLoaded()
          }) { throwable: Throwable? -> Timber.d(throwable) }
      compositeDisposable.add(disposable)
    }
  }

  override fun onItemClick(data: SearchItem) {
    view.navigateToPlayer(data.id.videoId)
  }

}

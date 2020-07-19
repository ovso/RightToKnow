package io.github.ovso.righttoknow.ui.vfacilitydetail

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import io.github.ovso.righttoknow.App.Companion.instance
import io.github.ovso.righttoknow.R
import io.github.ovso.righttoknow.framework.network.GeocodeNetwork
import io.github.ovso.righttoknow.framework.network.model.GoogleGeocode
import io.github.ovso.righttoknow.framework.utils.AddressUtils
import io.github.ovso.righttoknow.framework.utils.TimeoutMillis
import io.github.ovso.righttoknow.ui.vfacilitydetail.model.VioFacDe
import io.github.ovso.righttoknow.ui.vfacilitydetail.model.ViolatorDe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import timber.log.Timber

class VFacilityDetailPresenterImpl internal constructor(private val view: VFacilityDetailPresenter.View) :
  VFacilityDetailPresenter {
  private val geocodeNetwork: GeocodeNetwork = GeocodeNetwork(
    instance.applicationContext, GeocodeNetwork.GEOCODING_BASE_URL
  )
  private var fullAddress: String? = null
  private var disposable: Disposable? = null
  private val compositeDisposable = CompositeDisposable()
  private var facName: String? = null
  override fun onCreate(savedInstanceState: Bundle?, intent: Intent) {
    view.setListener()
    view.hideButton()
    view.setSupportActionBar()
    view.showLoading()
    req(intent)
  }

  private fun req(intent: Intent) {
    if (intent.hasExtra("vio_fac_link")) {
      val link = intent.getStringExtra("vio_fac_link")
      compositeDisposable.add(
        Observable.fromCallable {
          val vioFacDe = VioFacDe.convertToItem(
            Jsoup.connect(link).timeout(TimeoutMillis.TIME.value).get()
          )
          facName = vioFacDe.vioFacName
          Timber.d("facName = %s", facName)
          fullAddress = vioFacDe.address
          VioFacDe.getContents(vioFacDe)
        }
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
            { o: String? ->
              view.showContents(o)
              view.showButton()
              view.hideLoading()
            }
          ) { throwable: Throwable? ->
            Timber.d(throwable)
            view.showMessage(R.string.error_server)
            view.hideLoading()
          }
      )
    } else if (intent.hasExtra("violator_link")) {
      val link = intent.getStringExtra("violator_link")
      compositeDisposable.add(
        Observable.fromCallable {
          val violatorDe = ViolatorDe.convertToItem(
            Jsoup.connect(link).timeout(TimeoutMillis.TIME.value).get()
          )
          facName = violatorDe.facName
          Timber.d("facName = %s", facName)
          fullAddress = violatorDe.address
          ViolatorDe.getContents(violatorDe)
        }
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
            { o: String? ->
              view.showContents(o)
              view.showButton()
              view.hideLoading()
            }
          ) { throwable: Throwable? ->
            Timber.d(throwable)
            view.showMessage(R.string.error_server)
            view.hideLoading()
          }
      )
    }
  }

  override fun onDestroy() {
    if (disposable != null) {
      disposable!!.dispose()
    }
  }

  override fun onMapClick(intent: Intent) {
    view.showLoading()
    if (!TextUtils.isEmpty(fullAddress)) {
      try {
        val address = AddressUtils.removeBracket(fullAddress)
        Timber.d("address = %s", address)
        disposable = geocodeNetwork
          .getGoogleGeocode(address)
          .map { googleGeocode: GoogleGeocode ->
            val locations = DoubleArray(2)
            if (googleGeocode.status.contains("OK")) {
              val location = googleGeocode.results[0].geometry.location
              locations[0] = location.lat
              locations[1] = location.lng
            } else {
              locations[0] = LAT_SEOUL
              locations[1] = LNG_SEOUL
            }
            locations
          }
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
            { locations: DoubleArray? ->
              if (!TextUtils.isEmpty(facName)) {
                view.navigateToMap(locations, facName)
                view.hideLoading()
              }
            }
          ) { throwable: Throwable? ->
            Timber.e(throwable)
            view.showMessage(R.string.error_not_found_address)
            view.hideLoading()
          }
      } catch (e: Exception) {
        Timber.e(e)
        view.showMessage(R.string.error_not_found_address)
        view.hideLoading()
      }
    } else {
      view.showMessage(R.string.error_not_found_address)
      view.hideLoading()
    }
  }

  override fun onRefresh(intent: Intent) {
    req(intent)
  }

  companion object {
    private const val LAT_SEOUL = 37.5652894
    private const val LNG_SEOUL = 126.8494635
  }

}
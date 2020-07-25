package io.github.ovso.righttoknow.data.network

import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import io.github.ovso.righttoknow.R

class Repository(
  private val firebase: Firebase
) {
  private val remoteConfig: FirebaseRemoteConfig = firebase.remoteConfig

  init {
    val configSettings = remoteConfigSettings {
      minimumFetchIntervalInSeconds = 3600
    }

    remoteConfig.setConfigSettingsAsync(configSettings)
    remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
  }

  fun fetchAdsData(): Task<Boolean> = remoteConfig.fetchAndActivate()
  fun getAdsValue(key: String): String = remoteConfig[key].asString()
}

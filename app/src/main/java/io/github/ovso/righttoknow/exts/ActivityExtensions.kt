package io.github.ovso.righttoknow.exts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding(
  crossinline bindingInflater: (LayoutInflater) -> T
) =
  lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
  }


inline fun <reified T : Any> Fragment.launchActivity(
  requestCode: Int = -1,
  options: Bundle? = null,
  noinline init: Intent.() -> Unit = {}
) {
  val intent = newIntent<T>(requireContext())
  intent.init()
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
    startActivityForResult(intent, requestCode, options)
  } else {
    startActivityForResult(intent, requestCode)
  }
}
inline fun <reified T : Any> Activity.launchActivity(
  requestCode: Int = -1,
  options: Bundle? = null,
  noinline init: Intent.() -> Unit = {}
) {
  val intent = newIntent<T>(this)
  intent.init()
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
    startActivityForResult(intent, requestCode, options)
  } else {
    startActivityForResult(intent, requestCode)
  }
}

inline fun <reified T : Any> Context.launchActivity(
  options: Bundle? = null,
  noinline init: Intent.() -> Unit = {}
) {
  val intent = newIntent<T>(this)
  intent.init()
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
    startActivity(intent, options)
  } else {
    startActivity(intent)
  }
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
  Intent(context, T::class.java)

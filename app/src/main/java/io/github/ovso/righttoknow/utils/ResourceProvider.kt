package io.github.ovso.righttoknow.utils

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources.NotFoundException
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class ResourceProvider(private val context: Context) {
  fun getText(@StringRes resId: Int): CharSequence {
    return context.getText(resId)
  }

  fun getTextArray(@ArrayRes resId: Int): Array<CharSequence> {
    return context.resources.getTextArray(resId)
  }

  fun getQuantityText(@PluralsRes resId: Int, quantity: Int): CharSequence {
    return context.resources.getQuantityText(resId, quantity)
  }

  fun getString(@StringRes resId: Int): String {
    return context.getString(resId)
  }

  fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
    return context.getString(resId, *formatArgs)
  }

  fun getStringArray(@ArrayRes resId: Int): Array<String> {
    return context.resources.getStringArray(resId)
  }

  fun getQuantityString(@PluralsRes resId: Int, quantity: Int): String {
    return context.resources.getQuantityString(resId, quantity)
  }

  fun getQuantityString(@PluralsRes resId: Int, quantity: Int, vararg formatArgs: Any?): String {
    return context.resources.getQuantityString(resId, quantity, *formatArgs)
  }

  fun getInteger(@IntegerRes resId: Int): Int {
    return context.resources.getInteger(resId)
  }

  fun getIntArray(@ArrayRes resId: Int): IntArray {
    return context.resources.getIntArray(resId)
  }

  fun getBoolean(@BoolRes resId: Int): Boolean {
    return context.resources.getBoolean(resId)
  }

  fun getDimension(@DimenRes resId: Int): Float {
    return context.resources.getDimension(resId)
  }

  fun getDimensionPixelSize(@DimenRes resId: Int): Int {
    return context.resources.getDimensionPixelSize(resId)
  }

  fun getDimensionPixelOffset(@DimenRes resId: Int): Int {
    return context.resources.getDimensionPixelOffset(resId)
  }

  fun getDrawable(@DrawableRes resId: Int): Drawable? {
    return ContextCompat.getDrawable(context, resId)
  }

  @ColorInt
  fun getColor(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(context, resId)
  }

  fun getColorStateList(@ColorRes resId: Int): ColorStateList? {
    return ContextCompat.getColorStateList(context, resId)
  }

  @Throws(NotFoundException::class)
  fun getFont(@FontRes id: Int): Typeface? {
    return ResourcesCompat.getFont(context, id)
  }

  fun loadAnimation(@AnimRes id: Int): Animation {
    return AnimationUtils.loadAnimation(context, id)
  }

}
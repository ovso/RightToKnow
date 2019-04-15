package io.github.ovso.righttoknow.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.AnimRes;
import androidx.annotation.ArrayRes;
import androidx.annotation.BoolRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import javax.inject.Inject;

public class ResourceProvider {

  private final Context context;

  @Inject
  public ResourceProvider(Context context) {
    this.context = context;
  }

  @NonNull
  public CharSequence getText(@StringRes int resId) {
    return context.getText(resId);
  }

  @NonNull
  public CharSequence[] getTextArray(@ArrayRes int resId) {
    return context.getResources().getTextArray(resId);
  }

  @NonNull
  public CharSequence getQuantityText(@PluralsRes int resId, int quantity) {
    return context.getResources().getQuantityText(resId, quantity);
  }

  @NonNull
  public String getString(@StringRes int resId) {
    return context.getString(resId);
  }

  @NonNull
  public String getString(@StringRes int resId, Object... formatArgs) {
    return context.getString(resId, formatArgs);
  }

  @NonNull
  public String[] getStringArray(@ArrayRes int resId) {
    return context.getResources().getStringArray(resId);
  }

  @NonNull
  public String getQuantityString(@PluralsRes int resId, int quantity) {
    return context.getResources().getQuantityString(resId, quantity);
  }

  @NonNull
  public String getQuantityString(@PluralsRes int resId, int quantity, Object... formatArgs) {
    return context.getResources().getQuantityString(resId, quantity, formatArgs);
  }

  public int getInteger(@IntegerRes int resId) {
    return context.getResources().getInteger(resId);
  }

  @NonNull
  public int[] getIntArray(@ArrayRes int resId) {
    return context.getResources().getIntArray(resId);
  }

  public boolean getBoolean(@BoolRes int resId) {
    return context.getResources().getBoolean(resId);
  }

  public float getDimension(@DimenRes int resId) {
    return context.getResources().getDimension(resId);
  }

  public int getDimensionPixelSize(@DimenRes int resId) {
    return context.getResources().getDimensionPixelSize(resId);
  }

  public int getDimensionPixelOffset(@DimenRes int resId) {
    return context.getResources().getDimensionPixelOffset(resId);
  }

  public Drawable getDrawable(@DrawableRes int resId) {
    return ContextCompat.getDrawable(context, resId);
  }

  @ColorInt
  public int getColor(@ColorRes int resId) {
    return ContextCompat.getColor(context, resId);
  }

  public ColorStateList getColorStateList(@ColorRes int resId) {
    return ContextCompat.getColorStateList(context, resId);
  }

  @Nullable
  public Typeface getFont(@FontRes int id) throws Resources.NotFoundException {
    return ResourcesCompat.getFont(context, id);
  }

  public Animation loadAnimation(@AnimRes int id) {
    return AnimationUtils.loadAnimation(context, id);
  }
}
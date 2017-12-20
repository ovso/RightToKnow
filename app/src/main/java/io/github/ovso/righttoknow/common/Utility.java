package io.github.ovso.righttoknow.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jaeho on 2017. 8. 14
 */

public class Utility {

  public static boolean isOnline(Context context) {
    ConnectivityManager connMgr =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    return (networkInfo != null && networkInfo.isConnected());
  }

  public static void setStatusBarColor(Activity activity, int color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = activity.getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(color);
    }
  }

  public static String getEmojiByUnicode(int unicode) {
    return new String(Character.toChars(unicode));
  }

  public static String getActionEmoji(List<String> actions) {
    Resources res = MyApplication.getInstance().getResources();
    for (String action : actions) {
      if (action.contains(res.getString(R.string.abused)) || action.contains(
          res.getString(R.string.attack))) {

        return Utility.getEmojiByUnicode(Constants.EMOJI_ABUSED);
      }
    }

    return "";
  }

  public static String getActionEmoji(String[] actions) {
    Resources res = MyApplication.getInstance().getResources();
    for (String action : actions) {
      if (action.contains(res.getString(R.string.abused)) || action.contains(
          res.getString(R.string.attack))) {
        return Utility.getEmojiByUnicode(Constants.EMOJI_ABUSED);
      }
    }

    return "";
  }

  public static String getVersionName(Context context) {
    String versionName = "0.0.1";
    try {
      PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      versionName = info.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

    return versionName;
  }

  public static int getBuildVersion() {
    return Build.VERSION.SDK_INT;
  }

  public static String convertDate(String strDate, String pattern) {
    ////Wed, 13 Dec 2017 13:49:00 +0900
    SimpleDateFormat originFormat =
        new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    SimpleDateFormat replaceFormat = new SimpleDateFormat(pattern);

    Date originDate = new Date();
    try {
      originDate = originFormat.parse(strDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return replaceFormat.format(originDate);
  }
}

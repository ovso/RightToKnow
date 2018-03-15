package io.github.ovso.righttoknow.common;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import java.io.IOException;
import java.util.List;
import timber.log.Timber;

/**
 * Created by jaeho on 2018. 3. 15
 */

public class AddressUtils {

  public static double[] getFromLocation(Context context, String address) {
    Geocoder geocoder = new Geocoder(context);
    try {
      List<Address> addresses = geocoder.getFromLocationName(address, 1);
      Timber.d("addresses = " + address);
      double[] latlngArray = new double[2];
      latlngArray[0] = addresses.get(0).getLatitude();
      latlngArray[1] = addresses.get(0).getLongitude();
      return latlngArray;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String removeBracket(String fullAddress) {
    int beginIndex = fullAddress.indexOf(")") + 1;
    int endIndex = fullAddress.indexOf("(", 1);
    if(endIndex == -1) {
      endIndex = fullAddress.length();
    }
    return fullAddress.substring(beginIndex, endIndex).trim();
  }
}

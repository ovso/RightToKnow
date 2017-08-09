package io.github.ovso.righttoknow.main;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.app.MyApplication;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LocationAware
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener {
  private static final String TAG = "LocationAware";
  private Context mContext;
  private String mLastUpdateTime;
  private boolean mRequestingLocationUpdates;
  private LocationRequest mLocationRequest;
  private OnLocationListener mLocationListener;

  public LocationAware(Context context) {
    mContext = context;
    mRequestingLocationUpdates = false;
  }

  private GoogleApiClient mGoogleApiClient;

  private void buildGoogleApiClient() {
    if (mGoogleApiClient == null) {
      mGoogleApiClient = new GoogleApiClient.Builder(mContext).addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .addApi(LocationServices.API)
          .build();

      mGoogleApiClient.connect();

      if (mLocationRequest == null) {
        createLocationRequest();
      }
    }
  }

  protected void createLocationRequest() {
    mLocationRequest = new LocationRequest();
    mLocationRequest.setInterval(5000);
    mLocationRequest.setFastestInterval(5000);
    mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    LocationSettingsRequest.Builder builder =
        new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);

    final PendingResult<LocationSettingsResult> result =
        LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

    result.setResultCallback(locationSettingsResult -> {
      final Status status = locationSettingsResult.getStatus();
      switch (status.getStatusCode()) {
        case LocationSettingsStatusCodes.SUCCESS:
          // All location settings are satisfied. The client can
          // initialize location requests here.
          break;
        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
          // Location settings are not satisfied, but this can be fixed
          // by showing the user a dialog.
          // Show the dialog by calling startResolutionForResult(),
          // and check the result in onActivityResult().
          //                            status.startResolutionForResult(
          //                                    OuterClass.this,
          //                                    REQUEST_CHECK_SETTINGS);
          try {
            status.startResolutionForResult((Activity) mContext, 1);
          } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
          }
          break;
        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
          // Location settings are not satisfied. However, we have no way
          // to fix the settings so we won't show the dialog.
          break;
      }
    });
  }

  @Override public void onConnected(@Nullable Bundle bundle) {
    if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
      startLocationUpdates();
    }
  }

  public void start() {
    buildGoogleApiClient();
    if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
      startLocationUpdates();
    }
  }

  public void stop() {
    if (mGoogleApiClient != null) {
      stopLocationUpdates();
      if (mGoogleApiClient.isConnected()) {
        mGoogleApiClient.disconnect();
        mGoogleApiClient = null;
      }
    }
  }

  private void startLocationUpdates() {
    mRequestingLocationUpdates = true;
    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,
        this);
  }

  private void stopLocationUpdates() {
    if (mGoogleApiClient.isConnected()) {
      LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }
  }

  public void setLocationListener(OnLocationListener listener) {
    mLocationListener = listener;
  }

  @Override public void onConnectionSuspended(int i) {
    mGoogleApiClient.connect();
  }

  @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  @Override public void onLocationChanged(Location location) {
    if (mLocationListener != null) {
      mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
      mLocationListener.onLocationChanged(location.getLatitude(), location.getLongitude(),
          mLastUpdateTime);
      mLocationListener.onAddressChanged(
          getAddress(location.getLatitude(), location.getLongitude()));
      stop();
    }
    mRequestingLocationUpdates = false;
  }

  public interface OnLocationListener {
    void onLocationChanged(double latitude, double longitude, String date);

    void onAddressChanged(Address address);

    void onError(String error);
  }

  public interface OnRequestCallbackListener {
    void onSettingsChangeUnavailable();

    void onLocationSettingsSuccess();
  }

  private Address getAddress(double latitude, double longitude) {
    Context context = MyApplication.getInstance();
    Resources res = context.getResources();
    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
    String errorMessage = null;
    // Address found using the Geocoder.
    List<Address> addresses = null;

    try {
      // Using getFromLocation() returns an array of Addresses for the area immediately
      // surrounding the given latitude and longitude. The results are a best guess and are
      // not guaranteed to be accurate.
      addresses = geocoder.getFromLocation(latitude, longitude,
          // In this sample, we get just a single address.
          1);
    } catch (IOException ioException) {
      // Catch network or other I/O problems.
      errorMessage = res.getString(R.string.error_no_service);
      Log.e(TAG, errorMessage, ioException);
      if (mLocationListener != null) {
        mLocationListener.onError(errorMessage);
      }
    } catch (IllegalArgumentException illegalArgumentException) {
      // Catch invalid latitude or longitude values.
      errorMessage = res.getString(R.string.error_lat_lon);
      Log.e(TAG, errorMessage + ". " + "Latitude = " + latitude + ", Longitude = " + longitude,
          illegalArgumentException);
      mLocationListener.onError(errorMessage);
    }

    // Handle case where no address was found.
    if (addresses == null || addresses.size() == 0) {
      if (errorMessage.isEmpty()) {
        errorMessage = res.getString(R.string.error_not_found_address);
        Log.e(TAG, errorMessage);
        mLocationListener.onError(errorMessage);
      }
      return null;
    } else {
      Address address = addresses.get(0);
      ArrayList<String> addressFragments = new ArrayList<>();

      // Fetch the address lines using {@code getAddressLine},
      // join them, and send them to the thread. The {@link android.location.address}
      // class provides other options for fetching address details that you may prefer
      // to use. Here are some examples:
      // getLocality() ("Mountain View", for example)
      // getAdminArea() ("CA", for example)
      // getPostalCode() ("94043", for example)
      // getCountryCode() ("US", for example)
      // getCountryName() ("United States", for example)
      for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
        addressFragments.add(address.getAddressLine(i));
      }
      return address;
    }
  }

  public final static String getAddress2(double lat, double lng) {
    String nowAddress = "현재 위치를 확인 할 수 없습니다.";
    Geocoder geocoder = new Geocoder(MyApplication.getInstance(), Locale.KOREA);
    List<Address> address;
    try {
      if (geocoder != null) {
        //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
        //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
        address = geocoder.getFromLocation(lat, lng, 1);

        if (address != null && address.size() > 0) {
          // 주소 받아오기
          String currentLocationAddress = address.get(0).getAddressLine(0).toString();
          nowAddress = currentLocationAddress;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return nowAddress;
  }
}
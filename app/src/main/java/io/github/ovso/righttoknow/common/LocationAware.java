package io.github.ovso.righttoknow.common;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.text.DateFormat;
import java.util.Date;

public class LocationAware
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener {
  private Context mContext;
  private String mLastUpdateTime;
  private boolean mRequestingLocationUpdates;
  private LocationRequest mLocationRequest;
  private OnLocationListener mLocationListener;

  public LocationAware(Context context) {
    mContext = context;
    mRequestingLocationUpdates = false;

    //buildGoogleApiClient();
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
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

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
          if (mOnRequestCallbackListener != null) {
            mOnRequestCallbackListener.onLocationSettingsSuccess();
          }
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
          if (mOnRequestCallbackListener != null) {
            mOnRequestCallbackListener.onSettingsChangeUnavailable();
          }
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
    }
    mRequestingLocationUpdates = false;
  }

  public interface OnLocationListener {
    void onLocationChanged(double latitude, double longitude, String date);
  }

  private OnRequestCallbackListener mOnRequestCallbackListener;

  public void setOnRequestCallbackListener(OnRequestCallbackListener listener) {
    mOnRequestCallbackListener = listener;
  }

  public interface OnRequestCallbackListener {
    void onSettingsChangeUnavailable();

    void onLocationSettingsSuccess();
  }
}
package co.unipiloto.proyect;

import android.Manifest;
import android.app.LocaleManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class OdometerService extends Service {

    private final IBinder binder = new OdometerBinder();
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static double distanceInMeters;
    private Location lastLocation;
    private Location targetLocation;
    private static final double TARGET_LATITUDE = 4.6326698;
    private static final double TARGET_LONGITUDE = -74.0655505;

    public class OdometerBinder extends Binder {
        public OdometerService getService() {
            return OdometerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Inicializar la ubicación específica
        targetLocation = new Location("target");
        targetLocation.setLatitude(TARGET_LATITUDE);
        targetLocation.setLongitude(TARGET_LONGITUDE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    distanceInMeters += location.distanceTo(targetLocation);
                }
                lastLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        // Pedir actualizaciones de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    // Método que devuelve la distancia calculada
    public double getDistance() {
        return distanceInMeters / 1000;  // Retorna la distancia en kilómetros
    }

}

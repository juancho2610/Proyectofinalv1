package co.unipiloto.proyect;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MapaPruebaActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_prueba_activity);

        // Ejemplo de llamadas a cada función
        findViewById(R.id.btn_show_marker).setOnClickListener(v -> showLocationWithMarker());
        findViewById(R.id.btn_show_route).setOnClickListener(v -> showDirectionsBetweenTwoPoints(4.6323, -74.0653, 4.6097, -74.0817));
        findViewById(R.id.btn_show_current_location).setOnClickListener(v -> showCurrentLocation());
    }

    // 1. Función para mostrar ubicación específica con un marcador y comentario
    public void showLocationWithMarker() {
        Uri gmmIntentUri = Uri.parse("geo:4.6323,-74.0653?q=4.6323,-74.0653(Mi+Ubicacion)");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    // 2. Función para mostrar ruta entre dos puntos
    public void showDirectionsBetweenTwoPoints(double originLat, double originLng, double destLat, double destLng) {
        String url = "https://www.google.com/maps/dir/?api=1&origin="+ originLat + "," + originLng + "&destination=" + destLat + "," + destLng + "&travelmode=driving";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    // 3. Función para mostrar la ubicación actual
    public void showCurrentLocation() {
        Uri gmmIntentUri = Uri.parse("geo:4.5513,-74.1446?q=mi+ubicacion");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    // Solicitar permiso de ubicación
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            showCurrentLocation();
        }
    }

    // Respuesta de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCurrentLocation();
            } else {
                // El permiso fue denegado
            }
        }
    }


}

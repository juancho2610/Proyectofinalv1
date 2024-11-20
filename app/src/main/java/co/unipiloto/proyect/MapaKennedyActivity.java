package co.unipiloto.proyect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MapaKennedyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_kennedy_activity);

        // Ejemplo de llamadas a cada función
        findViewById(R.id.btn_show_marker_kennedy).setOnClickListener(v -> showLocationWithMarker());
        findViewById(R.id.btn_show_marker_kennedy_v1).setOnClickListener(v -> showLocationWithMarkerv1());
        findViewById(R.id.btn_show_route_kennedy).setOnClickListener(v -> showDirectionsBetweenTwoPoints(4.6288,-74.1752, 4.6419,-74.1589));
        findViewById(R.id.btn_show_route_kennedy_v1).setOnClickListener(v -> showDirectionsBetweenTwoPointsv1(4.6419,-74.1589, 4.6238,-74.1775));
        findViewById(R.id.btn_show_current_location_kennedy).setOnClickListener(v -> showCurrentLocation());
        findViewById(R.id.btn_show_current_location_kennedy_v1).setOnClickListener(v -> showCurrentLocationv1());

        toolbar = findViewById(R.id.toolbar_KENNEDY);
        setSupportActionBar(toolbar);
    }

    // 1. Función para mostrar ubicación específica con un marcador y comentario
    public void showLocationWithMarker() {
        Uri gmmIntentUri = Uri.parse("geo:4.6288,-74.1752?q=4.6288,-74.1752(Portal de las Americas)");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    // 1.1 Función para mostrar ubicación específica con un marcador y comentario
    public void showLocationWithMarkerv1() {
        Uri gmmIntentUri = Uri.parse("geo:4.6419,-74.1589?q=4.6419,-74.1589(C.C Tintal Plaza)");
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

    // 2.1 Función para mostrar ruta entre dos puntos
    public void showDirectionsBetweenTwoPointsv1(double originLat, double originLng, double destLat, double destLng) {
        String url = "https://www.google.com/maps/dir/?api=1&origin="+ originLat + "," + originLng + "&destination=" + destLat + "," + destLng + "&travelmode=driving";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    // 3. Función para mostrar la ubicación actual
    public void showCurrentLocation() {
        Uri gmmIntentUri = Uri.parse("geo:4.6288,-74.1752?q=mi+ubicacion actual");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    // 3.1 Función para mostrar la ubicación actual
    public void showCurrentLocationv1() {
        Uri gmmIntentUri = Uri.parse("geo:4.6419,-74.1589?q=mi+ubicacion actual");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    // Solicitar permiso de ubicación
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menu en el toolbar
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manejar las opciones del toolbar
        int id = item.getItemId();

        if (id == R.id.menu_volver) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}

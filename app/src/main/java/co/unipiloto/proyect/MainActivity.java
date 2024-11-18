package co.unipiloto.proyect;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonCreateAccount, buttonServicio;
    private DatabaseHelper dbHelper;
    private Toolbar toolbar;

    private OdometerService odometerService;
    private boolean bound = false;
    private TextView distanceView;
    private final Handler handler = new Handler();
    private static final int REQUEST_LOCATION_PERMISSION = 1;


    private FusedLocationProviderClient fusedLocationClient;

    // ServiceConnection para conectar el servicio
    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            OdometerService.OdometerBinder binder = (OdometerService.OdometerBinder) service;
            odometerService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        distanceView = findViewById(R.id.distanceView);
        displayDistance();

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        buttonServicio = findViewById(R.id.buttonService);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Verificar las credenciales
                loginUser(username, password);
            }


        });

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para abrir la actividad SignUpActivity
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        buttonServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotificationService.class);
                intent.putExtra("message", "!Inicio de sesion Terminada!");
                startService(intent);
            }
        });


        // Inicializar FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Verificar permisos y obtener ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }


    }

    private void loginUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {DatabaseHelper.COLUMN_PASSWORD};
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD));
            cursor.close();

            if (password.equals(storedPassword)) {
                // Inicio de sesión exitoso
                Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                // Si el inicio de sesión es exitoso, redireccionar a la página de reservas
                Intent intent = new Intent(MainActivity.this, RolesActivity.class);
                startActivity(intent);
            } else {
                // Contraseña incorrecta
                Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Nombre de usuario no encontrado
            Toast.makeText(MainActivity.this, "Nombre de usuario no encontrado", Toast.LENGTH_SHORT).show();
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

        if (id == R.id.menu_consultar_votos) {
            consultarVotosPorActividad();
            return true;
        } else if (id == R.id.menu_consultar_usuarios) {
            consultarUsuariosRegistrados();
            return true;
        } else if (id == R.id.menu_volver) {
            onBackPressed();
            return true;
        } else if (id == R.id.menu_horario) {
            Intent intent = new Intent(MainActivity.this, VerHorarioActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_ver_actividades_votos) {
            Intent intent = new Intent(MainActivity.this, VerActividadesconvotosActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Funcionalidad 1: Consultar la cantidad de votos de todas las actividades
    private void consultarVotosPorActividad() {
        Cursor cursor = dbHelper.getCantidadVotosPorActividad();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay votos registrados", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder resultado = new StringBuilder();
        while (cursor.moveToNext()) {
            String actividad = cursor.getString(0);
            int cantidadVotos = cursor.getInt(1);
            resultado.append(actividad).append(": ").append(cantidadVotos).append(" votos\n");
        }

        // Mostrar el resultado en un diálogo o cualquier otra vista
        new AlertDialog.Builder(this)
                .setTitle("Votos por Actividad")
                .setMessage(resultado.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    // Funcionalidad 2: Consultar los usuarios registrados
    private void consultarUsuariosRegistrados() {
        Cursor cursor = dbHelper.getUsuariosRegistrados();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay usuarios registrados", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder resultado = new StringBuilder();
        while (cursor.moveToNext()) {
            String usuario = cursor.getString(0);
            resultado.append("Usuario: ").append(usuario).append("\n");
        }

        // Mostrar el resultado en un diálogo o cualquier otra vista
        new AlertDialog.Builder(this)
                .setTitle("Usuarios Registrados")
                .setMessage(resultado.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            } else {
                bindService(new Intent(this, OdometerService.class), connection, BIND_AUTO_CREATE);
            }
        } else {
            bindService(new Intent(this, OdometerService.class), connection, BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bindService(new Intent(this, OdometerService.class), connection, BIND_AUTO_CREATE);
            } else {
                Toast.makeText(this, "Location permission required for distance calculation.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }

    // Método para mostrar la distancia en el TextView
    private void displayDistance() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (bound && odometerService != null) {
                    double distance = odometerService.getDistance();
                    distanceView.setText(String.format("Distance: %.2f km", distance));
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    // Método para obtener la ubicación actual
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = 4.6482976;
                            double longitude = -74.107807;
                            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            try {
                                List<Address> address = geocoder.getFromLocation(latitude, longitude, 1);
                                if (address != null && !address.isEmpty()) {
                                    String city = address.get(0).getLocality();
                                    String message = "Lat: " + latitude + ", Long: " + longitude + ", Cuidad: " + city;
                                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "No se pudo obtener la ubicación.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }



}
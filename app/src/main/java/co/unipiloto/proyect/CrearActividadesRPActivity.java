package co.unipiloto.proyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CrearActividadesRPActivity extends AppCompatActivity {

    private Toolbar toolbar;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_actividades_rp_activity);

        // Inicializar el helper de la base de datos
        dbHelper = new DatabaseHelper(this);

        toolbar=findViewById(R.id.toolbarCrearActividades_CA_RP);
        setSupportActionBar(toolbar);

        // Declarar los botones
        Button btnCrearActividades = findViewById(R.id.buttonCrearActividades_CA_RP);
        Button btnCrearDeportes = findViewById(R.id.buttonCrearDeportes_CA_RP);
        Button btnReservaRP = findViewById(R.id.btnReservarRP);
        Button btnMapa = findViewById(R.id.btnMapaRP);


        // Configurar el OnClickListener para "Crear Actividades"
        btnCrearActividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear una actividad
                Intent intent = new Intent(CrearActividadesRPActivity.this, RolPlaneador.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para "Crear Deportes"
        btnCrearDeportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear una actividad
                Intent intent = new Intent(CrearActividadesRPActivity.this, SeleccionDeportesActivity.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para "Ver Reserva"
        btnReservaRP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear una actividad
                Intent intent = new Intent(CrearActividadesRPActivity.this, ReservaActivity.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para "Mapa Prueba"
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear una actividad
                Intent intent = new Intent(CrearActividadesRPActivity.this, SeleccionMapaLocalidadActivity.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

    }

    private void abrirMapa(String localidad){
        Intent intent = new Intent(this, MapaLocalidadActivity.class);
        intent.putExtra("localidad", localidad);
        startActivity(intent);
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

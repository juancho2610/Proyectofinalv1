package co.unipiloto.proyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CrearActividadesRCActivity extends AppCompatActivity {

    private Toolbar toolbar;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_actividades_rc_activity);

        // Inicializar el helper de la base de datos
        dbHelper = new DatabaseHelper(this);

        toolbar=findViewById(R.id.toolbarCrearActividades_CA);
        setSupportActionBar(toolbar);

        // Declarar los botones
        Button btnCrearActividades = findViewById(R.id.buttonCrearActividades_CA);
        Button btnCrearDeportes = findViewById(R.id.buttonCrearDeportes_CA);
        Button btnReservaRC = findViewById(R.id.btnReservarRC);


        // Configurar el OnClickListener para "Crear Actividades"
        btnCrearActividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear una actividad
                Intent intent = new Intent(CrearActividadesRCActivity.this, RolCuidadano.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para "Crear Deportes"
        btnCrearDeportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear una actividad
                Intent intent = new Intent(CrearActividadesRCActivity.this, SeleccionDeportesActivity.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para "Ver Reserva"
        btnReservaRC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear una actividad
                Intent intent = new Intent(CrearActividadesRCActivity.this, ReservaActivity.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

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

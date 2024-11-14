package co.unipiloto.proyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SeleccionMapaLocalidadActivity extends AppCompatActivity {

    private Toolbar toolbar;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_seleccion_activity);

        // Inicializar el helper de la base de datos
        dbHelper = new DatabaseHelper(this);

        toolbar = findViewById(R.id.toolbarMapaSL);
        setSupportActionBar(toolbar);

        // Declarar los botones
        Button btnChapinero = findViewById(R.id.buttonMapaChapinero);
        Button btnKennedy = findViewById(R.id.buttonMapaKennedy);
        Button btnCuidadBolivar = findViewById(R.id.buttonMapaCuidadBolivar);
        Button btnSuba = findViewById(R.id.buttonMapaSuba);

        // Configurar el OnClickListener para el mapa
        btnChapinero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para el mapa
                Intent intent = new Intent(SeleccionMapaLocalidadActivity.this, MapaChapineroActivity.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para el mapa
        btnKennedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para el mapa
                Intent intent = new Intent(SeleccionMapaLocalidadActivity.this, MapaKennedyActivity.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para "Seleccionar Natacion"
        btnCuidadBolivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear un horario
                Intent intent = new Intent(SeleccionMapaLocalidadActivity.this, MapaCuidadBolivarActivity.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para "Seleccionar Baloncesto"
        btnSuba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear un horario
                Intent intent = new Intent(SeleccionMapaLocalidadActivity.this, MapaSubaActivity.class);
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

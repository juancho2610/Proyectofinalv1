package co.unipiloto.proyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SeleccionDeportesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deportes_seleccion_activity);

        // Inicializar el helper de la base de datos
        dbHelper = new DatabaseHelper(this);

        toolbar = findViewById(R.id.toolbarCrearActividades_CA);
        setSupportActionBar(toolbar);

        // Declarar los botones
        Button btnFutbol = findViewById(R.id.buttonCrearFutbol);
        Button btnVoleibol = findViewById(R.id.buttonCrearVoleibol);
        Button btnNatacion = findViewById(R.id.buttonCrearNatacion);
        Button btnBaloncesto = findViewById(R.id.buttonCrearBaloncesto);

        // Configurar el OnClickListener para "Seleccionar Futbol"
        btnFutbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear una actividad
                Intent intent = new Intent(SeleccionDeportesActivity.this, RolDeportes.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para "Seleccionar Voleibol"
        btnVoleibol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear un horario
                Intent intent = new Intent(SeleccionDeportesActivity.this, RolDeportes.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para "Seleccionar Natacion"
        btnNatacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear un horario
                Intent intent = new Intent(SeleccionDeportesActivity.this, RolDeportes.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para "Seleccionar Baloncesto"
        btnBaloncesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear un horario
                Intent intent = new Intent(SeleccionDeportesActivity.this, RolDeportes.class);
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

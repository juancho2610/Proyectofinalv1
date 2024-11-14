package co.unipiloto.proyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RolCuidadano extends AppCompatActivity {

    private Toolbar toolbar;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rolcuidadano_activity2);

        // Inicializar el helper de la base de datos
        dbHelper = new DatabaseHelper(this);

        toolbar=findViewById(R.id.toolbarCrearActividades_CA);
        setSupportActionBar(toolbar);

        // Declarar los botones
        Button btnCrear = findViewById(R.id.buttonCrearActividad_cuidadano);
        Button btnVer = findViewById(R.id.buttonVerActividades_cuidadano);
        Button btnModificar = findViewById(R.id.buttonModificarActividad_cuidadano);
        Button btnEliminar = findViewById(R.id.buttonEliminarActividad_cuidadano);
        Button btnVotar = findViewById(R.id.buttonVotarActividad_cuidadano);
        Button btnEliminarVoto = findViewById(R.id.buttonVotarActividadEliminar_cuidadano);

        // Configurar el OnClickListener para "Crear Actividad"
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear una actividad
                Intent intent = new Intent(RolCuidadano.this, CrearActividadActivity.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para "Ver Actividades Recientes"
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar la lista de actividades en otra actividad
                Intent intent = new Intent(RolCuidadano.this, VerActividadActivity.class);
                startActivity(intent); // Lanza una nueva actividad para ver actividades recientes
            }
        });

        // Configurar el OnClickListener para "Modificar Actividad"
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir una pantalla para seleccionar una actividad y modificarla
                Intent intent = new Intent(RolCuidadano.this, SeleccionarActividadEditarActivity.class);
                startActivity(intent); // Lanza una actividad donde se selecciona y modifica la actividad
            }
        });



        // Configurar el OnClickListener para "Eliminar Actividad"
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir una pantalla para seleccionar una actividad y eliminarla
                Intent intent = new Intent(RolCuidadano.this, EliminarActividadActivity.class);
                startActivity(intent); // Lanza una actividad donde se selecciona y elimina la actividad
            }
        });

        // Configurar el OnClickListener para "Votar Actividad"
        btnVotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir una pantalla para seleccionar una actividad y eliminarla
                Intent intent = new Intent(RolCuidadano.this, VotarActividadActivity.class);
                startActivity(intent); // Lanza una actividad donde se selecciona y elimina la actividad
            }
        });

        // Configurar el OnClickListener para " Eliminar un Voto"
        btnEliminarVoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir una pantalla para seleccionar una actividad y eliminarla
                Intent intent = new Intent(RolCuidadano.this, EliminarVotoActivity.class);
                startActivity(intent); // Lanza una actividad donde se selecciona y elimina la actividad
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

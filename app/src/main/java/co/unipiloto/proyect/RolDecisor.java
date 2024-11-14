package co.unipiloto.proyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RolDecisor extends AppCompatActivity {

    private Toolbar toolbar;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roldecisor_activity2);

        // Inicializar el helper de la base de datos
        dbHelper = new DatabaseHelper(this);

        toolbar=findViewById(R.id.toolbarCrearActividades_CA);
        setSupportActionBar(toolbar);

        // Declarar los botones
        Button btnVer = findViewById(R.id.buttonVerActividades_decisor);
        Button btnModificar = findViewById(R.id.buttonModificarActividad_decisor);
        Button btnEliminar = findViewById(R.id.buttonEliminarActividad_decisor);
        Button btnAceptar = findViewById(R.id.buttonAceptarActividad_decisor);

        // Configurar el OnClickListener para "Ver Actividades Recientes"
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar la lista de actividades en otra actividad
                Intent intent = new Intent(RolDecisor.this, VerActividadActivity.class);
                startActivity(intent); // Lanza una nueva actividad para ver actividades recientes
            }
        });

        // Configurar el OnClickListener para "Modificar Actividad"
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir una pantalla para seleccionar una actividad y modificarla
                Intent intent = new Intent(RolDecisor.this, SeleccionarActividadEditarActivity.class);
                startActivity(intent); // Lanza una actividad donde se selecciona y modifica la actividad
            }
        });

        // Configurar el OnClickListener para "Eliminar Actividad"
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir una pantalla para seleccionar una actividad y eliminarla
                Intent intent = new Intent(RolDecisor.this, EliminarActividadActivity.class);
                startActivity(intent); // Lanza una actividad donde se selecciona y elimina la actividad
            }
        });

        // Configurar el OnClickListener para "Aceptar Actividad"
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir una pantalla para seleccionar una actividad y aceptarla
                Intent intent = new Intent(RolDecisor.this, AceptarActividadActivity.class);
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

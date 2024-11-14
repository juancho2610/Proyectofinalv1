package co.unipiloto.proyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RolDeportes extends AppCompatActivity {

    private Toolbar toolbar;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roldeportes_activity);

        // Inicializar el helper de la base de datos
        dbHelper = new DatabaseHelper(this);

        toolbar=findViewById(R.id.toolbarCrearActividades_Deportes);
        setSupportActionBar(toolbar);

        // Declarar los botones
        Button btnCrear = findViewById(R.id.buttonCrear_Deportes);
        Button btnVer = findViewById(R.id.buttonVerHorarios_Deportes);
        Button btnGenerar = findViewById(R.id.buttonVerHorarios_Generados);

        // Configurar el OnClickListener para "Crear Actividad"
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir un formulario para crear un horario
                Intent intent = new Intent(RolDeportes.this, CrearHorarioActivity.class);
                startActivity(intent); // Lanza una nueva actividad donde el usuario creará la actividad
            }
        });

        // Configurar el OnClickListener para "Ver Horarios Recientes"
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar la lista de actividades en otra actividad
                Intent intent = new Intent(RolDeportes.this, VerHorarioActivity.class);
                startActivity(intent); // Lanza una nueva actividad para ver actividades recientes
            }
        });

        // Configurar el OnClickListener para "Ver Horarios Recientes"
        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar la lista de actividades en otra actividad
                Intent intent = new Intent(RolDeportes.this, HorarioVervunoActivity.class);
                startActivity(intent); // Lanza una nueva actividad para ver actividades recientes
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

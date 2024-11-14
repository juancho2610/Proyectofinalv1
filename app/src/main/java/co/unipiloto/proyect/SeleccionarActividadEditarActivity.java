package co.unipiloto.proyect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeleccionarActividadEditarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ArrayList<Integer> ids;
    ArrayList<String> nombres, descripciones, localidades, estados;
    EditarActividadAdapter editarActividadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_actividad_editar);

        recyclerView = findViewById(R.id.recyclerViewActividadesEditar_selec_editar);
        dbHelper = new DatabaseHelper(this);

        // Inicializar las listas
        ids = new ArrayList<>();
        nombres = new ArrayList<>();
        descripciones = new ArrayList<>();
        localidades = new ArrayList<>();
        estados = new ArrayList<>();

        toolbar=findViewById(R.id.toolbar_modificar);
        setSupportActionBar(toolbar);

        // Llenar las listas con las actividades de la base de datos
        cargarActividades();

        // Configurar el RecyclerView con el nuevo adaptador
        editarActividadAdapter = new EditarActividadAdapter(this, ids, nombres, descripciones, localidades, estados, new EditarActividadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                // Lanza la actividad para editar la actividad seleccionada
                Intent intent = new Intent(SeleccionarActividadEditarActivity.this, EditarActividadActivity.class);
                intent.putExtra("id", id);  // Pasa el ID de la actividad seleccionada
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(editarActividadAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    // Método para cargar las actividades desde la base de datos
    private void cargarActividades() {
        Cursor cursor = dbHelper.getAllActivities();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay actividades", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0)); // ID de la actividad
            nombres.add(cursor.getString(1)); // Nombre de la actividad
            descripciones.add(cursor.getString(2)); // Descripción
            localidades.add(cursor.getString(3)); // Ubicación
            estados.add(cursor.getString(4)); // Estado

            // Mostrar un mensaje con los nombres recuperados para verificar
            Log.d("Actividades", "Actividad: " + cursor.getString(1));
        }
    }
}

package co.unipiloto.proyect;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EliminarActividadActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ArrayList<Integer> ids;
    ArrayList<String> nombres, descripciones, ubicaciones, estados;
    EliminarAdapter actividadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_activity2);

        recyclerView = findViewById(R.id.recyclerViewActividadesEliminar_eliminar);
        dbHelper = new DatabaseHelper(this);

        toolbar=findViewById(R.id.toolbar_eliminar);
        setSupportActionBar(toolbar);

        // Inicializar listas
        ids = new ArrayList<>();
        nombres = new ArrayList<>();
        descripciones = new ArrayList<>();
        ubicaciones = new ArrayList<>();
        estados = new ArrayList<>();

        // Cargar actividades
        cargarActividades();

        // Configurar el RecyclerView y el adaptador
        actividadAdapter = new EliminarAdapter(this, ids, nombres, descripciones, ubicaciones, estados, new EliminarAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int id) {
                // Confirmación antes de eliminar
                new AlertDialog.Builder(EliminarActividadActivity.this)
                        .setTitle("Eliminar Actividad")
                        .setMessage("¿Estás seguro de que quieres eliminar esta actividad?")
                        .setPositiveButton("Sí", (dialog, which) -> eliminarActividad(id))
                        .setNegativeButton("No", null)
                        .show();
            }
        }) {
            @Override
            public void onItemClick(int id) {
                // Confirmación antes de eliminar
                new AlertDialog.Builder(EliminarActividadActivity.this)
                        .setTitle("Eliminar Actividad")
                        .setMessage("¿Estás seguro de que quieres eliminar esta actividad?")
                        .setPositiveButton("Sí", (dialog, which) -> eliminarActividad(id))
                        .setNegativeButton("No", null)
                        .show();

            }
        };
        recyclerView.setAdapter(actividadAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void cargarActividades() {
        Cursor cursor = dbHelper.getAllActivities();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay actividades", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0));  // ID de la actividad
            nombres.add(cursor.getString(1));  // Nombre de la actividad
            descripciones.add(cursor.getString(2));  // Descripción
            ubicaciones.add(cursor.getString(3));  // Ubicación
            estados.add(cursor.getString(4));  // Estado
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

    // Método para eliminar la actividad seleccionada
    private void eliminarActividad(int id) {
        boolean isDeleted = dbHelper.deleteActivity(id);
        if (isDeleted) {
            Toast.makeText(this, "Actividad eliminada", Toast.LENGTH_SHORT).show();
            // Recargar la lista de actividades
            recreate();
        } else {
            Toast.makeText(this, "Error al eliminar la actividad", Toast.LENGTH_SHORT).show();
        }
    }
}

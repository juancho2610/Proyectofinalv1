package co.unipiloto.proyect;

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

public class EliminarVotoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ArrayList<Integer> ids;
    ArrayList<String> nombres, descripciones;
    EliminarVotoAdapter eliactividadAdapter;
    String usuarioId;  // ID del usuario que inició sesión

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.votar_eliminar_activity);

        recyclerView = findViewById(R.id.recyclerViewActividades_eliminarvotar);
        dbHelper = new DatabaseHelper(this);

        toolbar=findViewById(R.id.toolbar_votar_eliminar);
        setSupportActionBar(toolbar);

        // ID del usuario actual (esto lo recibirías de tu sistema de autenticación)
        usuarioId = "juan" + "\nUsuario: laura" + "\nUsuario: carlos";  // Por ejemplo

        // Inicializar listas
        ids = new ArrayList<>();
        nombres = new ArrayList<>();
        descripciones = new ArrayList<>();

        // Cargar las actividades en las que el usuario ha votado
        cargarActividadesVotadas();

        // Configurar el RecyclerView y el adaptador
        eliactividadAdapter = new EliminarVotoAdapter(this, ids, nombres, descripciones, new EliminarVotoAdapter.OnEliminarVotoClickListener() {
            @Override
            public void onEliminarVotoClick(int actividadId) {
                eliminarVoto(actividadId);
            }
        });
        recyclerView.setAdapter(eliactividadAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Cargar las actividades donde el usuario ha votado
    private void cargarActividadesVotadas() {
        Cursor cursor = dbHelper.getActividadesVotadasPorUsuario(usuarioId);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No has votado en ninguna actividad", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0));  // ID de la actividad
            nombres.add(cursor.getString(1));  // Nombre de la actividad
            descripciones.add(cursor.getString(2));  // Descripción
        }
    }

    // Eliminar el voto de la actividad seleccionada
    private void eliminarVoto(int actividadId) {
        boolean votoEliminado = dbHelper.eliminarVoto(usuarioId, actividadId);
        if (votoEliminado) {
            Toast.makeText(this, "Voto eliminado", Toast.LENGTH_SHORT).show();
            recreate();  // Recargar la lista de actividades votadas
        } else {
            Toast.makeText(this, "Error al eliminar el voto", Toast.LENGTH_SHORT).show();
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

}

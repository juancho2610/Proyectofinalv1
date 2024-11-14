package co.unipiloto.proyect;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VotarActividadActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ArrayList<Integer> ids;
    ArrayList<String> nombres, descripciones;
    VotarAdapter votarAdapter;
    String usuarioId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.votar_activity);

        recyclerView = findViewById(R.id.recyclerViewVotar);
        dbHelper = new DatabaseHelper(this);

        toolbar=findViewById(R.id.toolbar_votar);
        setSupportActionBar(toolbar);

        // ID del usuario actual
        usuarioId ="juan" + "\nUsuario: laura" + "\nUsuario: carlos";

        // Inicializar listas
        ids = new ArrayList<>();
        nombres = new ArrayList<>();
        descripciones = new ArrayList<>();

        // Cargar actividades
        cargarActividades();

        // Configurar el RecyclerView y el adaptador
        votarAdapter = new VotarAdapter(this, ids, nombres,descripciones, new VotarAdapter.OnVotarClickListener() {

            @Override
            public void onVotarClick(int Id) {
                verificarYVotar(Id);
            }
        });
        recyclerView.setAdapter(votarAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Cargar actividades desde la base de datos
    private void cargarActividades() {
        Cursor cursor = dbHelper.getAllActivities();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay actividades disponibles", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0));  // ID de la actividad
            nombres.add(cursor.getString(1));  // Nombre
            descripciones.add(cursor.getString(2));  // Descripción
        }
    }

    // Verificar si el usuario ya votó por la actividad y, si no, registrar el voto
    private void verificarYVotar(int actividadId) {
        if (dbHelper.yaHaVotado(usuarioId, actividadId)) {
            Toast.makeText(this, "Ya has votado por esta actividad", Toast.LENGTH_SHORT).show();
        } else {
            boolean votoExitoso = dbHelper.registrarVoto(usuarioId, actividadId);
            if (votoExitoso) {
                Toast.makeText(this, "Voto registrado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al registrar el voto", Toast.LENGTH_SHORT).show();
            }
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

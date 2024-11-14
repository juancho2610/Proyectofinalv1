package co.unipiloto.proyect;

import android.content.Intent;
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

public class VerModificarHorarioActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ArrayList<Integer> ids;
    ArrayList<String> fechas, horas, localidades;
    EditarHorarioAdapter editarHorarioAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_modificar_horario_activity);

        recyclerView = findViewById(R.id.recyclerViewHorarios_ver_horario_mod);
        dbHelper = new DatabaseHelper(this);

        toolbar=findViewById(R.id.toolbarCrearActividades_ver_horario_mod);
        setSupportActionBar(toolbar);

        // Inicializar listas
        ids = new ArrayList<>();
        fechas = new ArrayList<>();
        horas = new ArrayList<>();
        localidades = new ArrayList<>();

        // Cargar los horarios desde la base de datos
        cargarHorarios();

        // Configurar el RecyclerView y el adaptador
        editarHorarioAdapter = new EditarHorarioAdapter(this, ids, fechas, horas, localidades, new EditarHorarioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                // Abrir la actividad para modificar el horario, pasando el ID
                Intent intent = new Intent(VerModificarHorarioActivity.this, SeleccionarHorarioEditarActivity.class);
                intent.putExtra("horarioId", id);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(editarHorarioAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    // Método para cargar los horarios de la base de datos
    private void cargarHorarios() {
        Cursor cursor = dbHelper.obtenerHorarios();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay horarios creados", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0));  // ID del horario
            fechas.add(cursor.getString(1));  // Fecha
            horas.add(cursor.getString(2));   // Hora
            localidades.add(cursor.getString(3));  // Localidad
        }
        cursor.close();
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

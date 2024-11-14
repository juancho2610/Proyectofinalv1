package co.unipiloto.proyect;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.database.CursorWindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VerHorarioActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DatabaseHelper dbHelper;
    private ArrayList<Integer> ids;
    private ArrayList<String> fechas, horas, localidades;
    private VerHorarioAdapter verHorarioAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_horario_activity);

        toolbar=findViewById(R.id.toolbarCrearActividades_ver_horario);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerViewHorarios_ver_horario);
        dbHelper = new DatabaseHelper(this);

        //Inicializar listas
        ids = new ArrayList<>();
        fechas = new ArrayList<>();
        horas = new ArrayList<>();
        localidades = new ArrayList<>();

        //Obtener los horarios desde la base de datos
        cargarHorarios();

        //Configurar el RecyclerView y el adaptador
        verHorarioAdapter = new VerHorarioAdapter(this, ids, fechas, horas, localidades, new VerHorarioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                Intent intent = new Intent(VerHorarioActivity.this, SeleccionarHorarioEditarActivity.class);
                intent.putExtra("horarioId", id);
                startActivity(intent);
            }
        },id -> {
            // Mostrar un cuadro de diálogo de confirmación antes de cancelar
            new AlertDialog.Builder(VerHorarioActivity.this)
                    .setTitle("Cancelar Horario")
                    .setMessage("¿Estás seguro de que deseas cancelar este horario?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Eliminar el horario de la base de datos
                        boolean eliminado = dbHelper.eliminarHorario(id);
                        if (eliminado) {
                            Toast.makeText(VerHorarioActivity.this, "Horario cancelado", Toast.LENGTH_SHORT).show();
                            cargarHorarios();  // Recargar la lista de horarios
                            verHorarioAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(VerHorarioActivity.this, "Error al cancelar el horario", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
        recyclerView.setAdapter(verHorarioAdapter);
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
            ids.add(cursor.getInt(0));
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

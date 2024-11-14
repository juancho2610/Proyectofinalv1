package co.unipiloto.proyect;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AceptarActividadActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ArrayList<Integer> ids;
    ArrayList<String> nombres, descripciones, ubicaciones, estados;
    AceptarAdapter actividadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aceptar_activity);

        recyclerView = findViewById(R.id.recyclerViewActividadesAceptar);
        dbHelper = new DatabaseHelper(this);

        // Inicializar listas
        ids = new ArrayList<>();
        nombres = new ArrayList<>();
        descripciones = new ArrayList<>();
        ubicaciones = new ArrayList<>();
        estados = new ArrayList<>();

        // Cargar actividades
        cargarActividades();

        // Configurar el RecyclerView y el adaptador
        actividadAdapter = new AceptarAdapter(this, ids, nombres, descripciones, ubicaciones, estados, new AceptarAdapter.OnAceptarClickListener() {
            @Override
            public void onAceptarClick(int id) {
                mostrarDialogoConfirmacion(id);
            }
        });
        recyclerView.setAdapter(actividadAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Cargar las actividades desde la base de datos
    private void cargarActividades() {
        Cursor cursor = dbHelper.getAllActivities();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay actividades", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0));  // ID
            nombres.add(cursor.getString(1));  // Nombre
            descripciones.add(cursor.getString(2));  // Descripción
            ubicaciones.add(cursor.getString(3));  // Ubicación
            estados.add(cursor.getString(4));  // Estado
        }
    }

    // Mostrar un diálogo de confirmación
    private void mostrarDialogoConfirmacion(int id) {
        new AlertDialog.Builder(this)
                .setTitle("Aceptar Actividad")
                .setMessage("¿Estás seguro de que deseas aceptar esta actividad?")
                .setPositiveButton("Aceptar", (dialog, which) -> aceptarActividad(id))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // Método para actualizar el estado de la actividad a "Aceptada"
    private void aceptarActividad(int id) {
        boolean updated = dbHelper.updateActivityEstado(id, "Aceptada");
        if (updated) {
            Toast.makeText(this, "Actividad aceptada", Toast.LENGTH_SHORT).show();
            recreate();  // Recargar la actividad para actualizar la lista
        } else {
            Toast.makeText(this, "Error al aceptar la actividad", Toast.LENGTH_SHORT).show();
        }
    }

}

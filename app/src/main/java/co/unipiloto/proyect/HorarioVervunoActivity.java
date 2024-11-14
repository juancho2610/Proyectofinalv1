package co.unipiloto.proyect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HorarioVervunoActivity extends AppCompatActivity {

    RecyclerView recyclerViewHorarios;
    DatabaseHelper dbHelper;
    ArrayList<Integer> ids;
    ArrayList<String> fechas, horas, estados, deportes;
    HorariovunoAdapter horariovunoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_horarios_activity);

        recyclerViewHorarios = findViewById(R.id.recyclerViewHorarios_actividadhorarios);
        dbHelper = new DatabaseHelper(this);

        // Inicializar listas
        ids = new ArrayList<>();
        fechas = new ArrayList<>();
        horas = new ArrayList<>();
        estados = new ArrayList<>();
        deportes = new ArrayList<>();

        // Cargar los horarios desde la base de datos
        cargarHorarios();

        // Configurar el RecyclerView y el adaptador
        horariovunoAdapter = new HorariovunoAdapter(this, ids, fechas, horas, estados, deportes,
                (id, accion) -> {
                    String usuarioActual = "usuarioEjemplo";

                    if (accion.equals("reservar")) {
                        boolean registrado = dbHelper.registrarHorariov(id, usuarioActual);
                        if (registrado) {
                            cargarHorarios();
                            horariovunoAdapter.notifyDataSetChanged();
                            Toast.makeText(this, "Horario registrado", Toast.LENGTH_SHORT).show();
                        }
                    } else if (accion.equals("cancelar")) {
                        boolean cancelado = dbHelper.cancelarHorariov(id);
                        if (cancelado) {
                            cargarHorarios();
                            horariovunoAdapter.notifyDataSetChanged();
                            Toast.makeText(this, "Reserva cancelada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        recyclerViewHorarios.setAdapter(horariovunoAdapter);
        recyclerViewHorarios.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnGenerarHorarios_actividadhorarios).setOnClickListener(v -> {
            dbHelper.generarHorariosAleatoriosDelMes();
            cargarHorarios();
            horariovunoAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Horarios generados para el mes", Toast.LENGTH_SHORT).show();
        });
    }

    private void cargarHorarios() {
        Cursor cursor = dbHelper.obtenerHorariosv();
        ids.clear();
        fechas.clear();
        horas.clear();
        estados.clear();
        deportes.clear();


        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0));
            fechas.add(cursor.getString(1));
            horas.add(cursor.getString(2));
            deportes.add(cursor.getString(3));
            estados.add(cursor.getString(4));
        }
        cursor.close();
    }

}

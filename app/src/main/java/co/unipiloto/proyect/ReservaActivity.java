package co.unipiloto.proyect;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReservaActivity extends AppCompatActivity {

    RecyclerView recyclerViewReservas;
    DatabaseHelper dbHelper;
    ArrayList<Integer> ids;
    ArrayList<String> fechas, horas, estados;
    ReservaAdapter reservaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_reserva_museo_activity);

        recyclerViewReservas = findViewById(R.id.recyclerViewReservas_ver_reserva);
        dbHelper = new DatabaseHelper(this);

        ids = new ArrayList<>();
        fechas = new ArrayList<>();
        horas = new ArrayList<>();
        estados = new ArrayList<>();

        cargarReservas();

        reservaAdapter = new ReservaAdapter(this, ids, fechas, horas, estados,
                (id, accion) -> {
                    if (accion.equals("reservar")) {
                        String usuarioActual = "usuarioEjemplo";
                        boolean registrado = dbHelper.reservarHorario(id, usuarioActual);
                        if (registrado) {
                            cargarReservas();
                            reservaAdapter.notifyDataSetChanged();
                            Toast.makeText(this, "Reserva registrada", Toast.LENGTH_SHORT).show();
                        }
                    } else if (accion.equals("cancelar")) {
                        boolean cancelado = dbHelper.cancelarReserva(id);
                        if (cancelado) {
                            cargarReservas();
                            reservaAdapter.notifyDataSetChanged();
                            Toast.makeText(this, "Reserva cancelada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        recyclerViewReservas.setAdapter(reservaAdapter);
        recyclerViewReservas.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnGenerarReservas_ver_reserva).setOnClickListener(v -> {
            dbHelper.generarReservasMuseo();
            cargarReservas();
            reservaAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Horarios generados para el mes", Toast.LENGTH_SHORT).show();
        });
    }

    private void cargarReservas() {
        Cursor cursor = dbHelper.obtenerReservas();
        ids.clear();
        fechas.clear();
        horas.clear();
        estados.clear();

        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0));
            fechas.add(cursor.getString(1));
            horas.add(cursor.getString(2));
            estados.add(cursor.getString(3));
        }
        cursor.close();
    }


}

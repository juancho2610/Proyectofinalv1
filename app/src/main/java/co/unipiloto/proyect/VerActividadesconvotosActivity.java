package co.unipiloto.proyect;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VerActividadesconvotosActivity extends AppCompatActivity {

    RecyclerView recyclerViewActividadesVotos;
    DatabaseHelper dbHelper;
    ArrayList<String> nombres, descripciones, ubicacion, estados;
    ArrayList<Integer> votos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_actividadesconvotos_activity);

        recyclerViewActividadesVotos = findViewById(R.id.recyclerViewActividadesVotos_actividadesconvotos);
        dbHelper = new DatabaseHelper(this);

        nombres = new ArrayList<>();
        descripciones = new ArrayList<>();
        ubicacion = new ArrayList<>();
        estados = new ArrayList<>();
        votos = new ArrayList<>();

        cargarActividadesConVotos();

        ActividadesconVotosAdapter adapter = new ActividadesconVotosAdapter(this, nombres,descripciones,ubicacion,estados, votos);
        recyclerViewActividadesVotos.setAdapter(adapter);
        recyclerViewActividadesVotos.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("Range")
    private void cargarActividadesConVotos() {
        Cursor cursor = dbHelper.obtenerActividadesConVotos();
        if (cursor.moveToFirst()) {
            do {
                nombres.add(cursor.getString(cursor.getColumnIndex("nombre")));
                descripciones.add(cursor.getString(cursor.getColumnIndex("descripcion")));
                ubicacion.add(cursor.getString(cursor.getColumnIndex("ubicacion")));
                estados.add(cursor.getString(cursor.getColumnIndex("estado")));
                votos.add(cursor.getInt(cursor.getColumnIndex("votos")));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


}

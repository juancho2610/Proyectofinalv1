package co.unipiloto.proyect;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VerActividadActivity extends AppCompatActivity {


    private Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ArrayList<String> nombres, descripciones, localidades, estados;
    ActividadAdapter actividadAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_actividad_activity2);

        recyclerView = findViewById(R.id.recyclerViewVerActividades);
        dbHelper = new DatabaseHelper(this);

        toolbar=findViewById(R.id.toolbarCrearActividades_CA);
        setSupportActionBar(toolbar);

        //Inicializar Listas
        nombres = new ArrayList<>();
        descripciones = new ArrayList<>();
        localidades = new ArrayList<>();
        estados = new ArrayList<>();

        //LLamar para obtener actividades
        cargarActividadesRecientes();

        //Configurar el Recyclerview y el adaptador
        actividadAdapter = new ActividadAdapter(this, nombres,descripciones,localidades,estados);
        recyclerView.setAdapter(actividadAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //Metodo obtener actividades de la base de datos
    private void cargarActividadesRecientes() {
        Cursor cursor = dbHelper.getAllActivities();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay actividades recientes", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()){
            nombres.add(cursor.getString(1));
            descripciones.add(cursor.getString(2));
            localidades.add(cursor.getString(3));
            estados.add(cursor.getString(4));
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

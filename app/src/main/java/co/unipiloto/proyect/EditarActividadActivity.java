package co.unipiloto.proyect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EditarActividadActivity extends AppCompatActivity {

    EditText ednombre,eddescripcion,edestado;
    Spinner spinnerlocalidad;
    Button btnguardar;
    DatabaseHelper dbHelper;
    int actividadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_actividad_activity2);

        //Inicializar elementos
        ednombre = findViewById(R.id.editTextNombreP);
        eddescripcion = findViewById(R.id.editTextDescripcion);
        edestado = findViewById(R.id.editTextEstado);
        spinnerlocalidad = findViewById(R.id.spinnerLocalidad);
        btnguardar = findViewById(R.id.buttonGuardar);
        dbHelper = new DatabaseHelper(this);

        // Obtener el ID de la actividad desde el intent
        actividadId = getIntent().getIntExtra("id", -1);

        // Cargar los datos actuales de la actividad
        cargarDatosActividad();

        //Guardar cambios
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = ednombre.getText().toString();
                String descripcion = eddescripcion.getText().toString();
                String estado = edestado.getText().toString();
                String localidad = spinnerlocalidad.getSelectedItem().toString();

                //Actualizar la actividad en la base de datos
                boolean updated = dbHelper.updateActivity(actividadId, nombre,descripcion,localidad,estado);
                if (updated){
                    Toast.makeText(EditarActividadActivity.this,"Actividad actualizada", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(EditarActividadActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Metodo para cargaar los datos de la actividad seleccionada
    private void cargarDatosActividad(){
        Cursor cursor = dbHelper.getActivityById(actividadId);
        if (cursor.moveToFirst()) {
            ednombre.setText(cursor.getString(1)); // Nombre
            eddescripcion.setText(cursor.getString(2)); // Descripción
            // Seleccionar la ubicación en el Spinner
            String[] localidades = {"Chapinero", "Kennedy", "Ciudad Bolívar", "Suba"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, localidades);
            spinnerlocalidad.setAdapter(adapter);

            String ubicacionActual = cursor.getString(3); // Ubicación actual
            if (ubicacionActual != null) {
                int spinnerPosition = adapter.getPosition(ubicacionActual);
                spinnerlocalidad.setSelection(spinnerPosition); // Seleccionar la ubicación correcta
            }
            edestado.setText(cursor.getString(4));
        }
    }
}

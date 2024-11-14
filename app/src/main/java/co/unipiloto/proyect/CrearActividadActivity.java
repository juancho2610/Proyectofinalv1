package co.unipiloto.proyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CrearActividadActivity extends AppCompatActivity {

    EditText editNombre, editDescripcion, editEstado;
    Spinner spinnerLocalidad;
    Button btnGuardar;
    DatabaseHelper dbHelper;
    NotificationService actividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_actividad_activity2);

        dbHelper = new DatabaseHelper(this);
        // Encontrar los campos y el botón
        editNombre = findViewById(R.id.editTextNombreP);
        editDescripcion = findViewById(R.id.editTextDescripcion);
        editEstado = findViewById(R.id.editTextEstado);
        btnGuardar = findViewById(R.id.buttonGuardar);
        spinnerLocalidad=findViewById(R.id.spinnerLocalidad);

        //Crear un array para las localidades
        String[] localidades={"Chapinero","Kennedy","Cuidad Bolivar","Suba"};

        //Crear un arrayAdapter para el Spinner
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, localidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Asignamos el Adapter al Spinner
        spinnerLocalidad.setAdapter(adapter);


        // Guardar la actividad en la base de datos
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editNombre.getText().toString();
                String descripcion = editDescripcion.getText().toString();
                String localidad = spinnerLocalidad.getSelectedItem().toString();
                String estado = editEstado.getText().toString();

                //Insertar la actividad en la base de datos
                boolean insert = dbHelper.addActivity(nombre, descripcion, localidad, estado);
                if (insert) {
                    Toast.makeText(CrearActividadActivity.this, "Actividad creada", Toast.LENGTH_SHORT).show();
                    finish(); // Cierra la actividad y vuelve a la pantalla anterior
                } else {
                    Toast.makeText(CrearActividadActivity.this, "Error al crear actividad", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


}

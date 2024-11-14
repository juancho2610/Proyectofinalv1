package co.unipiloto.proyect;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class CrearHorarioActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textViewFecha, textViewHora;
    private Spinner spinnerlocalidad;
    private Button btnSelccionarFecha, btnSeleccionarHora, btnCrearHorario;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_horario_activity);

        //Inicializar vistas
        textViewFecha = findViewById(R.id.textViewFecha);
        textViewHora = findViewById(R.id.textViewHora);
        spinnerlocalidad = findViewById(R.id.spinnerLocalidad);
        btnSelccionarFecha = findViewById(R.id.btnSeleccionarFecha);
        btnSeleccionarHora = findViewById(R.id.btnSeleccionarHora);
        btnCrearHorario = findViewById(R.id.btnCrearHorario);

        dbHelper = new DatabaseHelper(this);

        toolbar=findViewById(R.id.toolbarCrearActividades_Horario);
        setSupportActionBar(toolbar);

        //Configurar el Spinner de localidad
        configurarSpinnerLocalidad();

        //Configurar el selector de fecha
        btnSelccionarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorDeFecha();
            }
        });

        //Configurar el selector de hora
        btnSeleccionarHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorDeHora();
            }
        });

        //Crear el horario al hacer click en el boton crear "horario"
        btnCrearHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearHorario();
            }
        });
    }

    // Método para configurar el Spinner de localidades
    private void configurarSpinnerLocalidad() {
        String[] localidades = {"Chapinero", "Kennedy", "Ciudad Bolívar", "Suba"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, localidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerlocalidad.setAdapter(adapter);
    }

    // Método para mostrar el DatePickerDialog y seleccionar la fecha
    private void mostrarSelectorDeFecha() {
        Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int anio = calendario.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anioSeleccionado, int mesSeleccionado, int diaSeleccionado) {
                String fechaSeleccionada = diaSeleccionado + "/" + (mesSeleccionado + 1) + "/" + anioSeleccionado;
                textViewFecha.setText(fechaSeleccionada);
            }
        }, anio, mes, dia);
        datePickerDialog.show();
    }

    // Método para mostrar el TimePickerDialog y seleccionar la hora
    private void mostrarSelectorDeHora() {
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int horaSeleccionada, int minutoSeleccionado) {
                String horaSeleccionadaTexto = horaSeleccionada + ":" + String.format("%02d", minutoSeleccionado);
                textViewHora.setText(horaSeleccionadaTexto);
            }
        }, hora, minutos, true);
        timePickerDialog.show();
    }

    // Método para crear el horario y almacenarlo en la base de datos
    private void crearHorario() {
        String fecha = textViewFecha.getText().toString();
        String hora = textViewHora.getText().toString();
        String localidad = spinnerlocalidad.getSelectedItem().toString();

        if (fecha.isEmpty() || hora.isEmpty() || localidad.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean exito = dbHelper.insertarHorario(fecha, hora, localidad);
        if (exito) {
            mostrarResumenHorario(fecha, hora, localidad);
        } else {
            Toast.makeText(this, "Error al crear el horario", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para mostrar un resumen del horario creado
    private void mostrarResumenHorario(String fecha, String hora, String localidad) {
        String mensaje = "Fecha: " + fecha + "\nHora: " + hora + "\nLocalidad: " + localidad;

        new AlertDialog.Builder(this)
                .setTitle("Resumen del Horario")
                .setMessage(mensaje)
                .setPositiveButton("OK", null)
                .show();
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

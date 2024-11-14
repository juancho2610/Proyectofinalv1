package co.unipiloto.proyect;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class SeleccionarHorarioEditarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textViewFecha, textViewHora;
    private Spinner spinnerlocalidad;
    private Button btnSeleccionarFecha, btnSeleccionarHora, btnGuardarCambios;
    private DatabaseHelper dbHelper;
    private int horarioId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_horario_activity);

        horarioId = getIntent().getIntExtra("horarioId", -1);

        // Inicializar vistas
        textViewFecha = findViewById(R.id.textViewFecha_mod_horario);
        textViewHora = findViewById(R.id.textViewHora_mod_horario);
        spinnerlocalidad = findViewById(R.id.spinnerLocalidad_mod_horario);
        btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha_mod_horario);
        btnSeleccionarHora = findViewById(R.id.btnSeleccionarHora_mod_horario);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios_mod_horario);

        dbHelper = new DatabaseHelper(this);

        toolbar=findViewById(R.id.toolbarCrearActividades_ver_horario_mod);
        setSupportActionBar(toolbar);

        // Cargar los datos del horario
        cargarDatosHorario();

        // Configurar el selector de fecha y hora
        btnSeleccionarFecha.setOnClickListener(v -> mostrarSelectorDeFecha());
        btnSeleccionarHora.setOnClickListener(v -> mostrarSelectorDeHora());

        // Guardar los cambios al hacer clic en el botón
        btnGuardarCambios.setOnClickListener(v -> guardarCambios());

    }

    // Método para cargar los datos del horario seleccionado
    private void cargarDatosHorario() {
        Cursor cursor = dbHelper.obtenerHorarioPorId(horarioId);
        if (cursor.moveToFirst()) {
            textViewFecha.setText(cursor.getString(1));  // Fecha
            textViewHora.setText(cursor.getString(2));   // Hora
            // Configurar el spinner de localidad
            configurarSpinnerLocalidad(cursor.getString(3));  // Localidad
        }
        cursor.close();
    }

    // Método para configurar el Spinner y seleccionar la localidad actual
    private void configurarSpinnerLocalidad(String localidadActual) {
        String[] localidades = {"Chapinero", "Kennedy", "Ciudad Bolívar", "Suba"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, localidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerlocalidad.setAdapter(adapter);

        int posicion = adapter.getPosition(localidadActual);
        spinnerlocalidad.setSelection(posicion);
    }

    // Método para mostrar el DatePickerDialog
    private void mostrarSelectorDeFecha() {
        // Implementar el DatePickerDialog (igual que en CrearHorarioActivity)
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

    // Método para mostrar el TimePickerDialog
    private void mostrarSelectorDeHora() {
        // Implementar el TimePickerDialog (igual que en CrearHorarioActivity)
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

    // Método para guardar los cambios en la base de datos
    private void guardarCambios() {
        String fecha = textViewFecha.getText().toString();
        String hora = textViewHora.getText().toString();
        String localidad = spinnerlocalidad.getSelectedItem().toString();

        // Actualizar el horario en la base de datos
        boolean exito = dbHelper.actualizarHorario(horarioId, fecha, hora, localidad);

        if (exito) {
            Toast.makeText(this, "Horario actualizado", Toast.LENGTH_SHORT).show();
            finish();  // Volver a la actividad anterior
        } else {
            Toast.makeText(this, "Error al actualizar el horario", Toast.LENGTH_SHORT).show();
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

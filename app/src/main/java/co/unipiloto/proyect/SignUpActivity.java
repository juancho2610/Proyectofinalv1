package co.unipiloto.proyect;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.GregorianCalendar;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextFullName, editTextEmail, editTextCuidad, editTextPassword, editTextConfirmPassword;
    private RadioGroup radioGroupGender;
    private Button buttonCreateAccount;
    private Button buttonSelectFechaNacimiento;
    private DatabaseHelper dbHelper;
    String selectedFechaNacimiento;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity2);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextCuidad = findViewById(R.id.editTextCiudad);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        buttonSelectFechaNacimiento = findViewById(R.id.buttonSelectFechaNacimiento);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);

        dbHelper = new DatabaseHelper(this);

        buttonSelectFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crear un Intent para abrir la actividad MainActivity
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);

                // Obtener los valores de los campos
                String username = editTextUsername.getText().toString();
                String fullName = editTextFullName.getText().toString();
                String email = editTextEmail.getText().toString();
                String cuidad = editTextCuidad.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                String gender = getSelectedGender();

                // Verificar si las contraseñas coinciden
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insertar usuario en la base de datos
                insertUser(username, fullName, email,cuidad,selectedFechaNacimiento, password, gender);

            }
        });

    }

    // Método para mostrar el DatePickerDialog
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                SignUpActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //Formateo la fecha seleccionada
                        selectedFechaNacimiento = dayOfMonth + "/" + (month + 1) + "/" + year;
                        //Verificamos si el usuario tiene mas de 18 años
                        if(isUserOlderThan18(year,month,dayOfMonth)){
                            //Mostrar la fecha seleccionada en el boton si es valida
                            buttonSelectFechaNacimiento.setText(selectedFechaNacimiento);
                        }else{
                            //Si no tiene mas dde 18 años, mostramos un mensaje y pedimos una nueva fecha
                            Toast.makeText(SignUpActivity.this,"Debes tener mas de 18 años", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    // Método para calcular si el usuario tiene más de 18 años
    private boolean isUserOlderThan18(int year, int month, int day) {
        // Obtenemos la fecha actual
        Calendar today = Calendar.getInstance();

        // Creamos un calendario con la fecha de nacimiento seleccionada
        Calendar birthDate = new GregorianCalendar(year, month, day);

        // Calculamos la edad en años
        int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // Si aún no ha pasado el cumpleaños este año, restamos 1 a la edad
        if (today.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) &&
                        today.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        // Devolvemos true si la edad es mayor o igual a 18
        return age >= 18;
    }

    private String getSelectedGender() {
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        return radioButton.getText().toString();
    }

    private void insertUser(String username, String fullName, String email, String cuidad, String fechanacimiento, String password, String gender) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USERNAME, username);
        values.put(DatabaseHelper.COLUMN_FULL_NAME, fullName);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_CUIDAD, cuidad);
        values.put(DatabaseHelper.COLUMN_FECHANACIMIENTO, fechanacimiento);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
        values.put(DatabaseHelper.COLUMN_GENDER, gender);

        // Insertar la nueva fila en la tabla
        long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);

        // Verificar si la inserción fue exitosa
        if (newRowId != -1) {
            // Mostrar mensaje de éxito
            Toast.makeText(SignUpActivity.this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show();
        } else {
            // Mostrar mensaje de error
            Toast.makeText(SignUpActivity.this, "Error al crear la cuenta", Toast.LENGTH_SHORT).show();
        }
    }

}

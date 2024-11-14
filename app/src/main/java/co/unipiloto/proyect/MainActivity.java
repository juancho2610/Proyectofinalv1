package co.unipiloto.proyect;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonCreateAccount,buttonServicio;
    private DatabaseHelper dbHelper;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        buttonServicio = findViewById(R.id.buttonService);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Verificar las credenciales
                loginUser(username, password);
            }



        });

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para abrir la actividad SignUpActivity
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        buttonServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotificationService.class);
                intent.putExtra("message", "!Inicio de sesion Terminada!");
                startService(intent);
            }
        });


    }

    private void loginUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {DatabaseHelper.COLUMN_PASSWORD};
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD));
            cursor.close();

            if (password.equals(storedPassword)) {
                // Inicio de sesión exitoso
                Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                // Si el inicio de sesión es exitoso, redireccionar a la página de reservas
                Intent intent = new Intent(MainActivity.this, RolesActivity.class);
                startActivity(intent);
            } else {
                // Contraseña incorrecta
                Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Nombre de usuario no encontrado
            Toast.makeText(MainActivity.this, "Nombre de usuario no encontrado", Toast.LENGTH_SHORT).show();
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

        if (id == R.id.menu_consultar_votos) {
            consultarVotosPorActividad();
            return true;
        } else if (id == R.id.menu_consultar_usuarios) {
            consultarUsuariosRegistrados();
            return true;
        } else if (id == R.id.menu_volver) {
            onBackPressed();
            return true;
        } else if (id == R.id.menu_horario){
            Intent intent = new Intent(MainActivity.this, VerHorarioActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.menu_ver_actividades_votos){
            Intent intent = new Intent(MainActivity.this, VerActividadesconvotosActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Funcionalidad 1: Consultar la cantidad de votos de todas las actividades
    private void consultarVotosPorActividad() {
        Cursor cursor = dbHelper.getCantidadVotosPorActividad();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay votos registrados", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder resultado = new StringBuilder();
        while (cursor.moveToNext()) {
            String actividad = cursor.getString(0);
            int cantidadVotos = cursor.getInt(1);
            resultado.append(actividad).append(": ").append(cantidadVotos).append(" votos\n");
        }

        // Mostrar el resultado en un diálogo o cualquier otra vista
        new AlertDialog.Builder(this)
                .setTitle("Votos por Actividad")
                .setMessage(resultado.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    // Funcionalidad 2: Consultar los usuarios registrados
    private void consultarUsuariosRegistrados() {
        Cursor cursor = dbHelper.getUsuariosRegistrados();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay usuarios registrados", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder resultado = new StringBuilder();
        while (cursor.moveToNext()) {
            String usuario = cursor.getString(0);
            resultado.append("Usuario: ").append(usuario).append("\n");
        }

        // Mostrar el resultado en un diálogo o cualquier otra vista
        new AlertDialog.Builder(this)
                .setTitle("Usuarios Registrados")
                .setMessage(resultado.toString())
                .setPositiveButton("OK", null)
                .show();
    }
}
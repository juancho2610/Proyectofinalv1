package co.unipiloto.proyect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
public class RolesActivity extends AppCompatActivity {

    private Toolbar toolbarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roles_activity2);


        Button buttonCiudadano = findViewById(R.id.buttonCiudadano);
        Button buttonPlaneador = findViewById(R.id.buttonPlaneador);
        Button buttonDecisor = findViewById(R.id.buttonDecisor);

        toolbarr=findViewById(R.id.toolbar_rol);
        setSupportActionBar(toolbarr);


        buttonCiudadano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RolesActivity.this, "Rol Ciudadano seleccionado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RolesActivity.this, CrearActividadesRCActivity.class);
                startActivity(intent);
            }
        });

        buttonPlaneador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RolesActivity.this, "Rol Planeador seleccionado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RolesActivity.this, CrearActividadesRPActivity.class);
                startActivity(intent);
            }
        });

        buttonDecisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RolesActivity.this, "Rol Decisor seleccionado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RolesActivity.this, CrearActividadesRDActivity.class);
                startActivity(intent);
            }
        });
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

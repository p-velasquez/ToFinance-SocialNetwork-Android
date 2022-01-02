package com.example.objetivo09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button button, sucursal;
    private EditText correo, contrasena;
    private final String ip = "192.168.1.97";


    String biografia, idUsuario, nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.loginBtn);
        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.password);
        sucursal = findViewById(R.id.sucursalBtn);
        recuperarPreferencias();

        button.setOnClickListener(view -> ejecutarServicio());

        sucursal.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        });
    }

    private void ejecutarServicio() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/bdproyecto9/login_validation.php", response -> {
            if (!response.isEmpty()) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject object = jsonObject.getJSONObject("perfil_load");
                    idUsuario = object.getString("ID_USUARIO");
                    nombreUsuario = object.getString("NOMBRE_USUARIO");
                    biografia = object.getString("BIOGRAFIA");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                guardarPreferencias();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Autenticación exitosa", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectas.", Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("correo", correo.getText().toString());
                parametros.put("contrasena", contrasena.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // Cambia de interfaz a la de Registro
    public void registrar(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private void guardarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("correo", correo.getText().toString());
        editor.putString("contrasena", contrasena.getText().toString());
        editor.putString("id_usuario", idUsuario);
        editor.putString("nombre_usuario", nombreUsuario);
        editor.putString("biografia", biografia);
        editor.putBoolean("session", true);
        editor.apply();
    }

    public void recuperarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        correo.setText(preferences.getString("usuario", ""));
        contrasena.setText(preferences.getString("contrasena", ""));
    }

}
package com.example.objetivo09;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    Button button;
    EditText usuario, correo, contrasena, contrasena2;
    String ip  = "192.168.1.97";
    private static final String PATRON_MAIL
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register);
        button = findViewById(R.id.registerBtn);
        usuario = findViewById(R.id.usuario);
        correo = findViewById(R.id.correoReg);
        contrasena = findViewById(R.id.registerPsw);
        contrasena2 = findViewById(R.id.registerPsw2);


        button.setOnClickListener(view -> {

            if ((!usuario.getText().toString().isEmpty() && !correo.getText().toString().isEmpty()) && (!contrasena.getText().toString().isEmpty() && !contrasena2.getText().toString().isEmpty())) {

                nameUserVali("http://" + ip + "/bdproyecto9/user_name_validation.php");


            } else {
                Toast.makeText(getApplicationContext(), "Campos vacios", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ejecutarServicio(String URL) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            if (validarCorreo(correo.getText().toString())) {
                if (contrasena.getText().toString().equals(contrasena2.getText().toString())) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "El correo ingresado es invalido", Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(RegisterActivity.this, "error ejecServ" + error.toString(), Toast.LENGTH_SHORT).show()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("Content-Type", "application/json; charset=utf-8");
                parametros.put("correo", correo.getText().toString());
                parametros.put("contrasena", contrasena.getText().toString());
                parametros.put("nombre_usuario", usuario.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void iniciar(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public static boolean validarCorreo(String email) {
        return (email.matches(PATRON_MAIL));
    }

    public void correoVali(String URL) {

        String correoV = this.correo.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            if (response.equals("1")) {
                Toast.makeText(RegisterActivity.this, "Correo ya existe", Toast.LENGTH_SHORT).show();
            } else {
                ejecutarServicio("http://" + ip + "/bdproyecto9/insert_usuario.php");
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(RegisterActivity.this, "Error Volley registro" + error.toString(), Toast.LENGTH_SHORT).show();
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("correo", correoV);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void nameUserVali(String URL) {

        String usuarioV = this.usuario.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            if (response.equals("1")) {
                Toast.makeText(RegisterActivity.this, "Usuario en uso", Toast.LENGTH_SHORT).show();
            } else {
                correoVali("http://" + ip + "/bdproyecto9/register_validation.php");
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(RegisterActivity.this, "User error 2" + error.toString(), Toast.LENGTH_SHORT).show();
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("usuario", usuarioV);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
package com.example.objetivo09;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<PostClass> arrayList;
    private TextView nombreUsuario, biografiaUsuario;

    protected String  name, biografia, idUsuario;
    String ip = "192.168.1.97";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_profile);
        nombreUsuario = findViewById(R.id.tvName);
        biografiaUsuario = findViewById(R.id.etDesc);
        cargarPerfil("http://" + ip + "/bdproyecto9/get_perfil.php");
        try {
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_userpic);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext()
                                ,HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_notifications:
                        startActivity(new Intent(getApplicationContext()
                                ,NotificationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext()
                                ,SettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_userpic:
                        return true;
                    case R.id.nav_upload:
                        startActivity(new Intent(getApplicationContext()
                                ,UploadActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        getAllPost("http://" + ip + "/bdproyecto9/get_publicaciones_usuario.php");
    }

    public void cargarPerfil(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject object = jsonObject.getJSONObject("perfilLoad");

                idUsuario = object.getString("ID_USUARIO");
                name = object.getString("NOMBRE_USUARIO");
                nombreUsuario.setText(name);
                biografia = object.getString("BIOGRAFIA");
                biografiaUsuario.setText(biografia);

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error trycatch" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {

        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                String userid = preferences.getString("id_usuario", "");
                parametros.put("id_usuario", userid);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getAllPost(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("post");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String userId = object.getString("userid").trim();
                    String desc = object.getString("desc").trim();
                    String postId = object.getString("idpost").trim();
                    int idUsuario = Integer.parseInt(userId);
                    int idPost = Integer.parseInt(postId);

                    // Creacion de ArrayList con Publicaciones para el Home
                    arrayList = new ArrayList<>();
                    recyclerView = findViewById(R.id.recyclerView);
                    arrayList.add(new PostClass(idUsuario, nombreUsuario.getText().toString() , R.drawable.doggo, idPost, desc, R.drawable.happydoggo));


                    // Adapta el ArrayList para que el RecyclerView los pueda mostrar segun el layout de post.xml
                    RecyclerAdapterHome recyclerAdapter = new RecyclerAdapterHome(arrayList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    recyclerView.setAdapter(recyclerAdapter);

                }

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error trycatch" + e.toString(), Toast.LENGTH_SHORT).show();
            }

        }, error -> Toast.makeText(getApplicationContext(), "Error volley" + error.toString(), Toast.LENGTH_SHORT).show()) {
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                parametros.put("fk_id_usuario", preferences.getString("id_usuario",""));
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
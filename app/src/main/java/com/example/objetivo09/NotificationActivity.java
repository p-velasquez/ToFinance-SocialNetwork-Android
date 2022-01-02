package com.example.objetivo09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Notification> listaNotificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_notification);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_notifications);
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
                        return true;
                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext()
                                ,SettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_userpic:
                        startActivity(new Intent(getApplicationContext()
                                ,ProfileActivity.class));
                        overridePendingTransition(0,0);
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

        listaNotificacion = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewNotification);
        listaNotificacion.add(new Notification("comentó","keka_galindo","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("comentó","armando_torres","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("le dió me gusta a","juanito_perez","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("comentó","elena_nito","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("quiere financiar","aquiles_baeza","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("comentó","keka_galindo","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("comentó","armando_torres","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("le dió me gusta a","juanito_perez","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("comentó","elena_nito","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("quiere financiar","aquiles_baeza","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("comentó","keka_galindo","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("comentó","armando_torres","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("le dió me gusta a","juanito_perez","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("comentó","elena_nito","Publicación de Prueba",R.drawable.doggo));
        listaNotificacion.add(new Notification("quiere financiar","aquiles_baeza","Publicación de Prueba",R.drawable.doggo));

        RecyclerAdapterNotification recyclerAdapter = new RecyclerAdapterNotification(listaNotificacion);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
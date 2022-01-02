package com.example.objetivo09;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    ArrayList<SettingsClass> listaSettings = new ArrayList<>();

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        llenarSettings();
        ListView lvSettings = (ListView) view.findViewById(R.id.lvSettings);

        ArrayAdapter<SettingsClass> lvAdapter = new ArrayAdapter<SettingsClass>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                listaSettings
        );

        lvSettings.setAdapter(lvAdapter);
        lvSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Toast.makeText(getActivity(), "Fragment editar perfil", Toast.LENGTH_SHORT).show();
                } else if (position == 1){
                    Toast.makeText(getActivity(), "Fragment cambiar contraseña", Toast.LENGTH_SHORT).show();
                }else if(position == 2){
                    cerrarSesion();
                }
            }
        });
        return view;
    }

    public void llenarSettings() {
        listaSettings.add(new SettingsClass("Editar Perfil"));
        listaSettings.add(new SettingsClass("Cambiar Contraseña"));
        listaSettings.add(new SettingsClass("Cerrar sesión"));
    }

    public void cerrarSesion() {
        //Impide al usuario volver a navegar si cerró sesión, sin el metodo el usuario puede cerrar sesion pero seguir navegando si retrocede
        SharedPreferences preferences = getActivity().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
package com.miempresa.noqa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Button;

public class vista_mapa extends AppCompatActivity implements maps.OnFragmentInteractionListener {

    private Button boton_restaurantes;
    private boolean boolBtnrestaruantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_mapa);

        boolBtnrestaruantes = false;

        Fragment fragmento = new maps();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragmento).commit();


    }
}
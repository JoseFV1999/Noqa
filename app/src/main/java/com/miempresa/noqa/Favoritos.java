package com.miempresa.noqa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Favoritos extends AppCompatActivity {

    private ListView listview;
    private ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        listview = (ListView) findViewById(R.id.lista_favoritos);

        names = new ArrayList<String>();
        names.add("Recorrido 1");
        names.add("Recorrido 2");
        names.add("Recorrido 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);

        listview.setAdapter(adapter);
    }
}
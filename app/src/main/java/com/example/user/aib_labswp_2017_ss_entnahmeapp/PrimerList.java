package com.example.user.aib_labswp_2017_ss_entnahmeapp;

import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.os.Bundle;

/**
 * Created by Marvin on 30.04.2017.
 */
public class PrimerList extends AppCompatActivity {

    private Spinner spinner;
    private static final String[] items = {"Entnommen", "Nicht entnommen"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primerlist);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PrimerList.this,
                android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
}

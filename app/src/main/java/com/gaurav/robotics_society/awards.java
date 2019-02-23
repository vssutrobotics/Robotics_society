package com.gaurav.robotics_society;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by GAURAV on 23-01-2019.
 */

public class awards extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.awards);
        this.setTitle("Awards");
    }

    public void ROBOCON(View view) {
        Intent rob = new Intent(awards.this, robocon.class);
        startActivity(rob);

    }

    public void NATIONALS(View view) {
        Intent nat = new Intent(awards.this, nationals.class);
        startActivity(nat);

    }

    public void E_YANTRA(View view) {
        Intent eyantra = new Intent(awards.this, eyantra.class);
        startActivity(eyantra);

    }

    public void TECHFESTS(View view) {
        Intent tech = new Intent(awards.this, techfest.class);
        startActivity(tech);

    }

}

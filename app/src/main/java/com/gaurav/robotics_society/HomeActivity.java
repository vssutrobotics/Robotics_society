package com.gaurav.robotics_society;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by GAURAV on 23-01-2019.
 */

public class HomeActivity extends AppCompatActivity {
    private TextView btn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        getSupportActionBar().hide();

        btn1 = (TextView) findViewById(R.id.Button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Home_Page.class);
                startActivity(intent);
            }
        });

    }
}

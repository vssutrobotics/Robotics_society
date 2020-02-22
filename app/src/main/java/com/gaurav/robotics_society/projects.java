package com.gaurav.robotics_society;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gaurav.robotics_society.Adapters.project_adp;
import com.gaurav.robotics_society.Models.project_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 12-02-2019.
 */

public class projects extends AppCompatActivity {

    private RecyclerView recycler;
    private project_adp project;
    private List<project_Model> products = new ArrayList<project_Model>();
    private DatabaseReference dbbproducts;

    private ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_main);

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait!");
        pd.show();

        this.setTitle("Projects");

        recycler = findViewById(R.id.project_recyclerView);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        products = new ArrayList<>();
        dbbproducts = FirebaseDatabase.getInstance().getReference("Project");
        dbbproducts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {

                    project_Model p = productSnapshot.getValue(project_Model.class);
                    project_Model ach = new project_Model();

                    String body = p.getHead();
                    String title = p.getImage();
                    String url = p.getUrl();

                    ach.setHead(body);
                    ach.setImage(title);
                    ach.setUrl(url);

                    products.add(ach);
                }

                project = new project_adp(projects.this, products);
                pd.hide();
                recycler.setAdapter(project);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pd.hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pd.hide();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pd.hide();
    }
}

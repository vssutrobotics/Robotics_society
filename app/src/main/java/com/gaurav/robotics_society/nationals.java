package com.gaurav.robotics_society;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gaurav.robotics_society.Adapters.robocon_adp;
import com.gaurav.robotics_society.Models.awards_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class nationals extends AppCompatActivity {

    private RecyclerView recycler;
    private robocon_adp events;
    private List<awards_Model> products = new ArrayList<awards_Model>();
    private DatabaseReference dbbproducts;

    private ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.awards_inside);
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait!");
        pd.show();

        this.setTitle("Nationals Awards");

        recycler = findViewById(R.id.awards_recyclerView);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        products = new ArrayList<>();
        dbbproducts = FirebaseDatabase.getInstance().getReference("nationals");
        dbbproducts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    awards_Model p = productSnapshot.getValue(awards_Model.class);
                    awards_Model ach = new awards_Model();

                    String head = p.getHead();
                    String desc = p.getDesc();

                    ach.setHead(head);
                    ach.setDesc(desc);

                    products.add(ach);
                }

                events = new robocon_adp(nationals.this, products);
                pd.hide();
                recycler.setAdapter(events);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

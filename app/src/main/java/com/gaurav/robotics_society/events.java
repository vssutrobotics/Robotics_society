package com.gaurav.robotics_society;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gaurav.robotics_society.Adapters.events_adp;
import com.gaurav.robotics_society.Models.events_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 17-01-2019.
 */

public class events extends AppCompatActivity {

    private RecyclerView recycler;
    private events_adp events;
    private List<events_Model> products = new ArrayList<events_Model>();
    private DatabaseReference dbbproducts;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait!");
        pd.show();

        this.setTitle("Notification");

        recycler = (RecyclerView) findViewById(R.id.events_recyclerView);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        products = new ArrayList<>();
        dbbproducts = FirebaseDatabase.getInstance().getReference("Events");
        dbbproducts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()){
                    events_Model p = productSnapshot.getValue(events_Model.class);
                    events_Model ach = new events_Model();

                    String body = p.getBody();
                    String title = p.getTitle();
                    String url = p.getUrl();

                    ach.setBody(body);
                    ach.setTitle(title);
                    ach.setUrl(url);

                    products.add(ach);
                }

                events = new events_adp(events.this, products);
                recycler.setAdapter(events);
                pd.hide();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pd.show();
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

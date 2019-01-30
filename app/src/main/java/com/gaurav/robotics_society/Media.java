package com.gaurav.robotics_society;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gaurav.robotics_society.Adapters.media_adp;
import com.gaurav.robotics_society.Models.media_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 20-01-2019.
 */

public class Media extends AppCompatActivity {

    private RecyclerView recycler;
    private media_adp medias;
    private List<media_Model> products = new ArrayList<media_Model>();
    private DatabaseReference dbproducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media);
        this.setTitle("Media Coverage");

        recycler = (RecyclerView) findViewById(R.id.media_recyclerView);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        products = new ArrayList<>();
        dbproducts = FirebaseDatabase.getInstance().getReference("Media");
        dbproducts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()){
                    media_Model m =  productSnapshot.getValue(media_Model.class);
                    media_Model med = new media_Model();
                    String date = m.getDate();
                    String Project= m.getProject();
                    String image = m.getImage();
                    String chn = m.getChannel();

                    med.setDate(date);
                    med.setChannel(chn);
                    med.setImage(image);
                    med.setProject(Project);

                    products.add(med);
                }

                medias = new media_adp(Media.this, products);
                recycler.setAdapter(medias);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

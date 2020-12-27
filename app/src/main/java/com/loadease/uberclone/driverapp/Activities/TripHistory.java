package com.loadease.uberclone.driverapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.Model.History;
import com.loadease.uberclone.driverapp.R;
import com.loadease.uberclone.driverapp.recyclerViewHistory.ClickListener;
import com.loadease.uberclone.driverapp.recyclerViewHistory.historyAdapter;

import java.util.ArrayList;

public class TripHistory extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference riderHistory;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView rvHistory;
    historyAdapter adapter;
    FirebaseAuth mAuth;
    ArrayList<History> listData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);

        initRecyclerView();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        riderHistory = database.getReference("Trips");
        listData = new ArrayList<>();
        adapter = new historyAdapter(this, listData, new ClickListener() {
            @Override
            public void onClick(View view, int index) {

            }
        });
        rvHistory.setAdapter(adapter);
        getHistory();
    }
    private void getHistory(){
      try {
          riderHistory.child(Common.userID).addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                      History history = postSnapshot.getValue(History.class);
                      listData.add(history);


//                    Toast.makeText(TripHistory.this, "array"+listData, Toast.LENGTH_SHORT).show();

                  }
                  if (listData != null) {
                      if (listData.size() < 1) {
                          Toast.makeText(getApplicationContext(), "You haven't took a ride yet!", Toast.LENGTH_SHORT).show();
                      } else {
                          adapter.notifyDataSetChanged();
                      }
                  }
              }
              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

//                Toast.makeText(TripHistory.this, "error", Toast.LENGTH_SHORT).show();
              }
          });
      }catch (Exception e) {
          Toast.makeText(this, "You haven't took a ride yet!", Toast.LENGTH_SHORT).show();
      }
    }
    private void initRecyclerView(){
        rvHistory = findViewById(R.id.rvHistory);
        rvHistory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvHistory.setLayoutManager(layoutManager);
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayoutManager.VERTICAL));
    }
}

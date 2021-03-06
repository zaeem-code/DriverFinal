package com.loadease.uberclone.driverapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loadease.uberclone.driverapp.Common.Common;

public class SelectRideType extends AppCompatActivity {

    CardView shehzorcard,mazdacard,pickupcard, truckcard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ride_type);

        mazdacard=findViewById(R.id.mazdacard);
        shehzorcard =findViewById(R.id.shehzorecard);
        pickupcard =findViewById(R.id.pickupcard);
        truckcard =findViewById(R.id.truckcard);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Validating...");
        fetchingData();
        if (dialog.isShowing()){

            dialog.dismiss();
        }
    }
boolean vehicalchk=false;
    String Typeofvehical="";
    public void onClickRide(View view) {

        switch (view.getId()){
            case R.id.mazda:
            case R.id.mazdalyt:
                vehicalchk=true;
                mazdacard.setCardBackgroundColor(Color.parseColor("#03528B"));
                shehzorcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                pickupcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                truckcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                Typeofvehical="Mazda";
                break;

            case R.id.shahzorelyt:
            case R.id.Shahzore:

                vehicalchk=true;
                mazdacard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                shehzorcard.setCardBackgroundColor(Color.parseColor("#03528B"));
                pickupcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                truckcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));

                Typeofvehical="Shahzore";

                break;


            case R.id.pickuplyt:
            case R.id.pickup:

                vehicalchk=true;
                mazdacard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                pickupcard.setCardBackgroundColor(Color.parseColor("#03528B"));
                shehzorcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                truckcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));

                Typeofvehical="Pickup";

                break;



            case R.id.truck:
            case R.id.trucklyt:

                vehicalchk=true;
                mazdacard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                truckcard.setCardBackgroundColor(Color.parseColor("#03528B"));
                shehzorcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                pickupcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));

                Typeofvehical="Truck";


                break;

            case R.id.confirm:
if (vehicalchk && !TextUtils.isEmpty(Typeofvehical)){
    RiderProfilechanging();
}else {
    Toast.makeText(this, "Nothing to Update", Toast.LENGTH_SHORT).show();
}
                break;


        }
    }
    private ProgressDialog dialog;

    private void RiderProfilechanging() {

        dialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("RidersProfile").child(Common.userID);

        myRef.child("carType").setValue(Typeofvehical).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Driverinfochanging();
            }
        });


    }
    private  void Driverinfochanging(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DriversInformation").child(Common.userID);

        myRef.child("carType").setValue(Typeofvehical).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (dialog.isShowing()){

                    dialog.dismiss();
                    Toast.makeText(SelectRideType.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    private void fetchingData() {

        dialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("RidersProfile").child(Common.userID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Typeofvehical = dataSnapshot.child("carType").getValue().toString();
                switch (Typeofvehical) {

                        case "Mazda":
                            mazdacard.setCardBackgroundColor(Color.parseColor("#03528B"));
                            shehzorcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                            pickupcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                            truckcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));

                            break;

                    case "Shahzore":

                            mazdacard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                            shehzorcard.setCardBackgroundColor(Color.parseColor("#03528B"));
                            pickupcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                            truckcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));


                            break;


                    case "Pickup":

                            mazdacard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                            pickupcard.setCardBackgroundColor(Color.parseColor("#03528B"));
                            shehzorcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                            truckcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));


                            break;



                        case "Truck":

                            mazdacard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                            truckcard.setCardBackgroundColor(Color.parseColor("#03528B"));
                            shehzorcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));
                            pickupcard.setCardBackgroundColor(Color.parseColor("#E5E5E5"));



                            break;
                }



            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }
}
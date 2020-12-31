
package com.loadease.uberclone.driverapp.Activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.R;
import com.loadease.uberclone.driverapp.Util.Rate;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;


public class RateActivity extends AppCompatActivity {
    Button btnSubmit;
    MaterialRatingBar ratingBar;
    MaterialEditText etComment;
String price="",duration="",KMS="";
    FirebaseDatabase database;
    TextView passengername;
    DatabaseReference rateDetailRef, driverInformationRef;
TextView fromadresstv,toaddresstv, totaldest,totaltime,totalfare;
de.hdodenhof.circleimageview.CircleImageView passengerPic;
ImageView call,chat;
    double ratingStars=1.0;
    String Currentpassengerid ="",Namepessenge="",Imagurl="",from="",To="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rate_trip);


        fromadresstv=findViewById(R.id.ed_current_loc);
        toaddresstv=findViewById(R.id.ed_drop_loc);
        totaldest =findViewById(R.id.txt_tot_distance);
        totaltime=findViewById(R.id.ed_tot_time);
        totalfare=findViewById(R.id.txt_fare_price);
        passengerPic =findViewById(R.id.img_driver);
        passengername =findViewById(R.id.txt_driver_name);
        btnSubmit=findViewById(R.id.btnSubmit);
        ratingBar=(MaterialRatingBar)findViewById(R.id.ratingBar);
        etComment=(MaterialEditText) findViewById(R.id.etComment);
        database= FirebaseDatabase.getInstance();
        rateDetailRef=database.getReference(Common.rate_detail_tbl);
        driverInformationRef=database.getReference(Common.user_driver_tbl);

        gettingRideDetails();
        ratingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
               ratingStars=rating;
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRateDetails();
            }
        });

    }


    private void submitRateDetails() {
        final AlertDialog alertDialog=new SpotsDialog.Builder().setContext(this).build();
        alertDialog.show();

         Rate rate=new Rate();
        rate.setRates(String.valueOf(ratingStars));
        rate.setComment(etComment.getText().toString());
        rate.setCustomername(Common.currentUser.getName());

        rate.setCustomerID(Currentpassengerid);
        rateDetailRef.child(Common.userID)

        .push()
        .setValue(rate)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                rateDetailRef.child(Common.userID)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                double averageStars=0.0;
                                int count=0;
                                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                                    Rate rate=postSnapshot.getValue(Rate.class);
                                    averageStars+=Double.parseDouble(rate.getRates());
                                    count++;
                                }
                                double finalAverge=averageStars/count;
                                DecimalFormat df=new DecimalFormat("#.#");
                                String valueUpdate=df.format(finalAverge);

                                Map<String, Object> driverUpdateRate=new HashMap<>();
                                driverUpdateRate.put("rates", valueUpdate);

                                driverInformationRef.child(Common.userID)
                                        .updateChildren(driverUpdateRate)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                alertDialog.dismiss();
                                                Toast.makeText(getApplicationContext(), "Thank you for submit", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                alertDialog.dismiss();
                                                Toast.makeText(getApplicationContext(), "Rate updated but can't write to driver information", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alertDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Rate failed!", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void Report(View view) {



        //redirect to complain center
    }
    private void gettingRideDetails() {


        Currentpassengerid =this.getIntent().getStringExtra("key");
        rateDetailRef.child(Currentpassengerid);
        To=this.getIntent().getStringExtra("destination");
        from=this.getIntent().getStringExtra("from");

        Namepessenge =this.getIntent().getStringExtra("name");
        Imagurl =this.getIntent().getStringExtra("imageurl");
        price =this.getIntent().getStringExtra("price");
        duration =this.getIntent().getStringExtra("duration");
        KMS =this.getIntent().getStringExtra("distance");

        fromadresstv.setText(from);
        toaddresstv.setText(To);
        totaltime.setText(duration);
        totaldest.setText(KMS);
        totalfare.setText(price);
        passengername.setText(Namepessenge);

        Picasso.get().load(Imagurl).into(passengerPic);

                }



}

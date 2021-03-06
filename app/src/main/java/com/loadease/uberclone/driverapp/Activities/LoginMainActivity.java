package com.loadease.uberclone.driverapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.Helpers.FirebaseHelper;
import com.loadease.uberclone.driverapp.Model.User;
import com.loadease.uberclone.driverapp.R;
import com.loadease.uberclone.driverapp.fragment.FragmentDriver;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginMainActivity extends AppCompatActivity {
    EditText pass, email;
    FirebaseAuth firebaseAuth;
    private ProgressDialog dialog;

    LinearLayout root;
    FirebaseHelper fh;
    TextView forgetPass;
    TextView note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_login);
        firebaseAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Validating...");
        pass = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);
        root = findViewById(R.id.bg);
        fh=new FirebaseHelper(this);
        forgetPass=findViewById(R.id.forgetpass);

          note=findViewById(R.id.note);





        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),ForgetPasswordActivity.class));

            }
        });








        findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(pass.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) || pass.getText().toString().length() < 8) {

                    Snackbar.make(root, "Invalid Credentials", Snackbar.LENGTH_SHORT).show();


                } else {
                    dialog.show();
                    login();

                }
            }
        });
    }






    private void login() {


        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
Common.userIDforfcm=firebaseAuth.getCurrentUser().getUid();
                getSharedPreferences("Login", MODE_PRIVATE).edit().putString("chk", "true").apply();
                loadata();

            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e) {

                Snackbar.make(root, getResources().getString(R.string.failed) + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });


    }
    private  void loadata(){

        FirebaseDatabase.getInstance().getReference(Common.user_driver_tbl)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()
                )
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//



                        Log.v("Hassan","Satust at server :"+ dataSnapshot.child("profile_status").getValue());


                        if(dataSnapshot.child("profile_status").getValue().toString().equals("incomplete")) {
                            Log.v("Hassan","Status incomplete");
                            if (dialog.isShowing()){
                                dialog.dismiss();
                            }
                            Common.userID=FirebaseAuth.getInstance().getCurrentUser().getUid();

                            getSharedPreferences("profile", MODE_PRIVATE).edit().putString("chk", "false").apply();
                            startActivity(new Intent(getApplicationContext(),signup_and_profile_Activity.class).putExtra("chk","Redirect")
                            .putExtra("name",dataSnapshot.child("name").getValue().toString())
                                    .putExtra("email",dataSnapshot.child("email").getValue().toString())
                                    .putExtra("pass",dataSnapshot.child("password").getValue().toString())



                                    .putExtra("phone",dataSnapshot.child("phone").getValue().toString()));

                        }
                        else
                            {  Log.v("Hassan","Status complete");

                                getSharedPreferences("profile", MODE_PRIVATE).edit().putString("chk", "true").apply();
                                DriverApproveStatus();
                        }

//

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    private void DriverApproveStatus() {
        if (!TextUtils.isEmpty(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
            FirebaseDatabase.getInstance().getReference(Common.user_driver_profile)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Common.currentRiderprofile = dataSnapshot.getValue(User.class);

                            if (dialog.isShowing()){
                                dialog.dismiss();
                            }

                            if (Common.currentRiderprofile.getProfile_status().toString().equals("Nverified")) {


                                getSharedPreferences("verified", MODE_PRIVATE).edit().putString("chk", "false").apply();

                                findViewById(R.id.forgetpass).setVisibility(View.GONE);
                                findViewById(R.id.l1).setVisibility(View.GONE);
                                findViewById(R.id.l2).setVisibility(View.GONE);
                                Button signin=findViewById(R.id.signin);
                                signin.setBackgroundColor(Color.RED);
                                signin.setClickable(false);

                                note.setText("Please Note :\nA Request Against Your Id is Sent to the LoadEase Office, You will soon be contacted by one of our officials, Please wait for verification process to complete, It may take a while to process");

                            } else if (Common.currentRiderprofile.getProfile_status().equals("verified")) {


                                getSharedPreferences("verified", MODE_PRIVATE).edit().putString("chk", "true").apply();
                                BlockedCHK();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }



    private void BlockedCHK(){

        FirebaseDatabase.getInstance().getReference(Common.user_driver_profile)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//



                        Log.v("Hassan","blick at server :"+ dataSnapshot.child("RidersProfile").getValue());


                        if(dataSnapshot.child("blocked").getValue().toString().equals("true")) {
                            Log.v("Hassan","Status incomplete");
                            if (dialog.isShowing()){
                                dialog.dismiss();
                            }
                            note.setText("Please Note :\nyou have been blocked\n Reason: "+dataSnapshot.child("blockedComments").getValue().toString());

                            getSharedPreferences("blocked", MODE_PRIVATE).edit().putString("chk", "true").apply();


                        }
                        else
                        {  Log.v("Hassan","not blocked complete");

                            Common.userID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                            getSharedPreferences("blocked", MODE_PRIVATE).edit().putString("chk", "false").apply();
                            new FirebaseHelper().LoadRiderProfile(getApplicationContext());
                        }

//

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

}
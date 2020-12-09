package com.loadease.uberclone.driverapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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
    MaterialEditText pass, email;
    FirebaseAuth firebaseAuth;
    private ProgressDialog dialog;

    LinearLayout root;
    FirebaseHelper fh;

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

                Common.userID=FirebaseAuth.getInstance().getCurrentUser().getUid();

                loadata();

            }
        }).addOnFailureListener(new OnFailureListener() {
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
                .child(Common.userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Common.currentRiderprofile = dataSnapshot.child("RidersProfile")
                                .child( Common.userID).getValue(User.class);

                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }

                        new FirebaseHelper().LoadRiderProfile(getApplicationContext());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
package com.loadease.uberclone.driverapp.Activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.Helpers.FirebaseHelper;
import com.loadease.uberclone.driverapp.Messages.Errors;
import com.loadease.uberclone.driverapp.Messages.Message;
import com.loadease.uberclone.driverapp.Model.User;
import com.loadease.uberclone.driverapp.R;
import com.loadease.uberclone.driverapp.fragment.FragmentDriver;

import java.util.Arrays;

import mehdi.sakout.fancybuttons.FancyButton;

public class Login extends AppCompatActivity {
    FirebaseHelper firebaseHelper;
    AccessToken accessToken = AccessToken.getCurrentAccessToken();
    boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseHelper = new FirebaseHelper(this);

        findViewById(R.id.btnSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseHelper.showRegistrerDialog();
            }
        });
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                firebaseHelper.showLoginDialog();
                Log.v("db","login");
            }
        });
        String blocked= getSharedPreferences("blocked", MODE_PRIVATE).getString("chk","false");
        String chk3= getSharedPreferences("profile", MODE_PRIVATE).getString("chk", "false") ;
        String chk2= getSharedPreferences("verified", MODE_PRIVATE).getString("chk", "false");
        String chk = getSharedPreferences("Login", MODE_PRIVATE).getString("chk", "false");
Log.v("cradenitiols",chk+", "+chk2+", "+chk3+", "+blocked);

            if (chk.equals("true") && chk3.equals("true")  && chk2.equals("true") )
            {

                if (blocked.equals("false"))
                {

                       startActivity(new Intent(Login.this, FragmentDriver.class));
                       finish();


                }
                else
                    {

                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(this, "Your Account is Blocked, Contact Head office for more Details", Toast.LENGTH_SHORT).show();
                   }


        }
    }




}
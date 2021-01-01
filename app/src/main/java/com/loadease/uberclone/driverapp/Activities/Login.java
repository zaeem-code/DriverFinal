package com.loadease.uberclone.driverapp.Activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
//        implements GoogleApiClient.OnConnectionFailedListener {
//    private GoogleApiClient googleApiClient;
//    public static final int SIGN_IN_CODE_GOOGLE = 157;
//    Button btnSignIn, btnLogIn;

    FirebaseHelper firebaseHelper;
//    GoogleSignInAccount account;
//
//    //facebook
//    CallbackManager mFacebookCallbackManager;
//    LoginManager mLoginManager;
    AccessToken accessToken = AccessToken.getCurrentAccessToken();
    boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseHelper = new FirebaseHelper(this);
//        FancyButton signInButtonGoogle = findViewById(R.id.login_button_Google);
//        FancyButton signInButtonFacebook = findViewById(R.id.facebookLogin);
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//                startActivityForResult(intent, SIGN_IN_CODE_GOOGLE);
//            }
//        });
//        setupFacebookStuff();
//        signInButtonFacebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (AccessToken.getCurrentAccessToken() != null) {
//                    mLoginManager.logOut();
//                } else {
//                    mLoginManager.logInWithReadPermissions(Login.this, Arrays.asList("email", "user_birthday", "public_profile"));
//                }
//            }
//        });
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
            }
        });
boolean blocked= getSharedPreferences("blocked", MODE_PRIVATE).getBoolean("chk", false);

        boolean chk3=
        getSharedPreferences("profile", MODE_PRIVATE).getBoolean("chk", false) ;
       boolean chk2=
               getSharedPreferences("Nverified", MODE_PRIVATE).getBoolean("chk", false);
            boolean chk = getSharedPreferences("Login", MODE_PRIVATE).getBoolean("chk", false);

            if (chk && chk2 && chk3) {
//                Toast.makeText(this, "in", Toast.LENGTH_SHORT).show();
                if (!blocked){
                startActivity(new Intent(Login.this, FragmentDriver.class));

                finish();}else {
                    Toast.makeText(this, "Your Account is Blocked, Contact Head office for more Details", Toast.LENGTH_SHORT).show();
                }


        }
    }

//    private void verifyGoogleAccount() {
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
//        if (opr.isDone()) {
//            GoogleSignInResult result = opr.get();
//            if (result.isSuccess())
//                firebaseHelper.loginSuccess();
//
//        }
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
////        verifyGoogleAccount();
//    }

//    private void setupFacebookStuff() {
//
//        // This should normally be on your application class
//        FacebookSdk.sdkInitialize(getApplicationContext());
//
//        mLoginManager = LoginManager.getInstance();
//        mFacebookCallbackManager = CallbackManager.Factory.create();
//
//        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                //login
//                firebaseHelper.registerByFacebookAccount();
//            }
//
//            @Override
//            public void onCancel() {
//                Toast.makeText(Login.this, "The login was canceled", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Toast.makeText(Login.this, "There was an error in the login", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == SIGN_IN_CODE_GOOGLE) {//Google
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void handleSignInResult(GoogleSignInResult result) {
//        if (result.isSuccess()) {
//            account = result.getSignInAccount();
//            firebaseHelper.registerByGoogleAccount(account);
//        } else {
//            Message.messageError(this, Errors.ERROR_LOGIN_GOOGLE);
//        }
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Message.messageError(this, Errors.ERROR_LOGIN_GOOGLE);
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }



}
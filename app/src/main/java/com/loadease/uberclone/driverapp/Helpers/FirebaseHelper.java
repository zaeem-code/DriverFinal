package com.loadease.uberclone.driverapp.Helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loadease.uberclone.driverapp.Activities.LoginMainActivity;
import com.loadease.uberclone.driverapp.Activities.signup_and_profile_Activity;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.Model.User;
import com.loadease.uberclone.driverapp.R;
import com.loadease.uberclone.driverapp.fragment.FragmentDriver;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class FirebaseHelper {
    AppCompatActivity activity;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;

    LinearLayout root;

    public FirebaseHelper(AppCompatActivity activity){
        this.activity=activity;
        root=activity.findViewById(R.id.root);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        users=firebaseDatabase.getReference(Common.user_driver_tbl);
        if(firebaseAuth.getUid()!=null)loginSuccess();
    }



    public FirebaseHelper() {
    }


    public   void LoadRiderProfile(Context context){

        Common.userID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("RidersProfile")
                .child(Common.userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.v("hassan",   "data ->>>>"+dataSnapshot.getValue(User.class));

                        Common.currentRiderprofile =dataSnapshot.getValue(User.class);

//                        context.startActivity(new Intent(context, FragmentDriver.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                           }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    public void showLoginDialog(){

        activity.getApplicationContext().startActivity(new Intent(activity, LoginMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    public void showRegistrerDialog(){
        activity.getApplicationContext().startActivity(new Intent(activity, signup_and_profile_Activity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }
    public void showRegisterPhone(final User user, final GoogleSignInAccount account){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(activity);
        alertDialog.setTitle(activity.getResources().getString(R.string.signin));
        alertDialog.setMessage(activity.getResources().getString(R.string.fill_fields));

        LayoutInflater inflater=LayoutInflater.from(activity);
        View register_phone_layout=inflater.inflate(R.layout.layout_register_phone, null);
        final MaterialEditText etPhone=register_phone_layout.findViewById(R.id.etPhone);

        alertDialog.setView(register_phone_layout);
        alertDialog.setPositiveButton(activity.getResources().getString(R.string.login), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                user.setEmail(account.getEmail());
                user.setName(account.getDisplayName());
                user.setPassword(null);
                user.setPhone(etPhone.getText().toString());
                user.setCarType("UberX");
                users.child(account.getId())
                        .setValue(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(root, activity.getResources().getString(R.string.registered), Snackbar.LENGTH_SHORT).show();
                                loginSuccess();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, activity.getResources().getString(R.string.failed)+e.getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                });
            }
        });
        alertDialog.setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
    public void showRegisterPhone(final User user, final String id, final String name, final String email){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(activity);
        alertDialog.setTitle(activity.getResources().getString(R.string.signin));
        alertDialog.setMessage(activity.getResources().getString(R.string.fill_fields));

        LayoutInflater inflater=LayoutInflater.from(activity);
        View register_phone_layout=inflater.inflate(R.layout.layout_register_phone, null);
        final MaterialEditText etPhone=register_phone_layout.findViewById(R.id.etPhone);

        alertDialog.setView(register_phone_layout);
        alertDialog.setPositiveButton(activity.getResources().getString(R.string.login), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                user.setEmail(email);
                user.setName(name);
                user.setPassword(null);
                user.setPhone(etPhone.getText().toString());
                user.setCarType("UberX");
                users.child(id)
                        .setValue(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(root, activity.getResources().getString(R.string.registered), Snackbar.LENGTH_SHORT).show();
                                loginSuccess();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, activity.getResources().getString(R.string.failed)+e.getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                });
            }
        });
        alertDialog.setNegativeButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoginManager.getInstance().logOut();
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
    public void loginSuccess(){
        goToMainActivity();
    }
    private void goToMainActivity(){
        activity.startActivity(new Intent(activity, FragmentDriver.class));
        activity.finish();
    }
    public void registerByGoogleAccount(final GoogleSignInAccount account){
        final User user=new User();
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User post = dataSnapshot.child(account.getId()).getValue(User.class);

                if(post==null) showRegisterPhone(user, account);
                else loginSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void registerByFacebookAccount(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        final String name=object.optString("name");
                        final String id=object.optString("id");
                        final String email=object.optString("email");
                        final User user=new User();
                        users.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User post = dataSnapshot.child(id).getValue(User.class);

                                if(post==null) showRegisterPhone(user, id, name, email);
                                else loginSuccess();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
        request.executeAsync();
    }
}

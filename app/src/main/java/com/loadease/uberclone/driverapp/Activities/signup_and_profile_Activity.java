package com.loadease.uberclone.driverapp.Activities;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.Helpers.FirebaseHelper;
import com.loadease.uberclone.driverapp.Model.User;
import com.loadease.uberclone.driverapp.R;

import java.io.ByteArrayOutputStream;

public class signup_and_profile_Activity extends  AppCompatActivity implements View.OnClickListener {
private ProgressDialog dialog;
        String imguri="";
        FirebaseAuth firebaseAuth;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference users;
        Button signup,uploadprofile;
        FirebaseUser currentuser=null;
        LinearLayout root;

        StorageReference mStorageRef;
        EditText etEmail, etPassword, etName, etYearofprodtution, etPhone, date, mont, year;
        String DOB,Roider_phn,Roider_name, gender = "male", RideType = "economy", Rider_CNIC_url, Rider_photo_url, Rider_car_photo_url, Rider_licence_photo_url, Yearofprodtution
        ,Roider_pass,Roider_email;
        RadioButton male, female, other,


        ecnomy, premium, bike, van, drone, truck;
        ImageView dropdownx,AddCNIC,ADDRIDERPIC,Addlicence,Addvehical;


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_and_profile);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Validating...");
        process();
        storagepermission();
        }

private void process() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etYearofprodtution = findViewById(R.id.Yearofprodtution);
        date = findViewById(R.id.date);
        mont = findViewById(R.id.month);
        year = findViewById(R.id.year);
        root = findViewById(R.id.bg);
        root = findViewById(R.id.bg);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(this);
        uploadprofile = findViewById(R.id.uploadprofile);
        uploadprofile.setOnClickListener(this);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        other = findViewById(R.id.other);


        dropdownx = findViewById(R.id.dropdownbuton);
        dropdownx.setOnClickListener(this);
        AddCNIC = findViewById(R.id.AddCNIC);
        AddCNIC.setOnClickListener(this);
        ADDRIDERPIC = findViewById(R.id.ADDRIDERPIC);
        ADDRIDERPIC.setOnClickListener(this);


        Addlicence = findViewById(R.id.ADDlicence);
        Addlicence.setOnClickListener(this);

        Addvehical = findViewById(R.id.ADDvehical);
        Addvehical.setOnClickListener(this);

        ecnomy = findViewById(R.id.ecnomy);
        premium = findViewById(R.id.premium);
        bike = findViewById(R.id.bike);
        van = findViewById(R.id.van);
        drone = findViewById(R.id.drone);
        truck = findViewById(R.id.truck);


        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference(Common.user_driver_tbl);


        }



        boolean dropdown = false;

@Override
public void onClick(View view) {
        switch (view.getId()) {


        case R.id.dropdownbuton:
        if (!dropdown) {
        dropdown = true;
        dropdownx.setRotation(180f);
        findViewById(R.id.dropdown_lyt).setVisibility(View.VISIBLE);
        } else {

        dropdownx.setRotation(0f);

        dropdown = false;
        findViewById(R.id.dropdown_lyt).setVisibility(View.GONE);
        }
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();

        break;
        case R.id.signup:
        if (TextUtils.isEmpty(etName.getText().toString())) {
        Snackbar.make(root,"Enter name first", Snackbar.LENGTH_SHORT).show();
        return;
        }
        else if (TextUtils.isEmpty(etPhone.getText().toString())) {
        Snackbar.make(root,"Enter Phone number first", Snackbar.LENGTH_SHORT).show();
        return;
        }else if (TextUtils.isEmpty(etEmail.getText().toString())) {
        Snackbar.make(root,"Enter Phone number first", Snackbar.LENGTH_SHORT).show();
        return;
        }else if (! etEmail.getText().toString().contains("@") || ! etEmail.getText().toString().contains(".com")) {
        Snackbar.make(root,"Enter Valid Email ", Snackbar.LENGTH_SHORT).show();
        return;
        }else if (TextUtils.isEmpty(etPassword.getText().toString())) {
        Snackbar.make(root,"Enter Phone number first", Snackbar.LENGTH_SHORT).show();
        return;
        }else if (etPassword.getText().toString().length()<8) {
        Snackbar.make(root,"password is too short", Snackbar.LENGTH_SHORT).show();
        return;
        }else {
        Roider_name=etName.getText().toString().trim();
        Roider_phn=etPhone.getText().toString().trim();
        Roider_email =etEmail.getText().toString().trim();
        Roider_pass=etPassword.getText().toString().trim();
        firebaseSignup("Signup");
        }
        break;
        case R.id.uploadprofile:
        if (TextUtils.isEmpty(RideType)) {
        Snackbar.make(root,"Error:  All fields needed to be filled", Snackbar.LENGTH_SHORT).show();
        return;
        }
        else if (TextUtils.isEmpty(Rider_car_photo_url)) {
        Snackbar.make(root,"Upload Car picture", Snackbar.LENGTH_SHORT).show();
        return;
        }else if (TextUtils.isEmpty(Rider_CNIC_url)) {
        Snackbar.make(root,"Upload Cnic picture", Snackbar.LENGTH_SHORT).show();
        return;
        }else if (TextUtils.isEmpty(Rider_licence_photo_url)) {
        Snackbar.make(root,"Upload Licence picture", Snackbar.LENGTH_SHORT).show();
        return;
        }else if (TextUtils.isEmpty(Rider_photo_url)) {
        Snackbar.make(root,"Upload Profile picture", Snackbar.LENGTH_SHORT).show();
        return;
        }else if (TextUtils.isEmpty(RideType)) {
        Snackbar.make(root,"Select Ride type", Snackbar.LENGTH_SHORT).show();
        return;
        }else if (TextUtils.isEmpty(gender)) {
        Snackbar.make(root,"select Gender picture", Snackbar.LENGTH_SHORT).show();
        return;
        }else if (TextUtils.isEmpty(date.getText().toString()) || TextUtils.isEmpty(mont.getText().toString())|| TextUtils.isEmpty(year.getText().toString())) {
        Snackbar.make(root,"Enter Valid date of birth", Snackbar.LENGTH_SHORT).show();
        return;
        } else if (TextUtils.isEmpty(etYearofprodtution.getText().toString())) {
        Snackbar.make(root,"Enter Year of production", Snackbar.LENGTH_SHORT).show();
        return;
        }else  {
        DOB=date.getText().toString().trim()+"-"+mont.getText().toString().trim()+"-"+year.getText().toString().trim();
        Yearofprodtution=etYearofprodtution.getText().toString();
        Roider_name=etName.getText().toString().trim();
        Roider_phn=etPhone.getText().toString().trim();
        Roider_email =etEmail.getText().toString().trim();
        Roider_pass=etPassword.getText().toString().trim();
        profilesignup();
        }
        break;
        case R.id.AddCNIC:
        if (TextUtils.isEmpty(etName.getText().toString())) {
        Snackbar.make(root,"Enter name first", Snackbar.LENGTH_SHORT).show();
        return;
        } else if (TextUtils.isEmpty(etPhone.getText().toString())) {
        Snackbar.make(root,"Enter Phone number first", Snackbar.LENGTH_SHORT).show();
        return;
        }else {
        opencamera(1);
        }
        break;
        case R.id.ADDRIDERPIC:
        if (TextUtils.isEmpty(etName.getText().toString())) {
        Snackbar.make(root,"Enter name first", Snackbar.LENGTH_SHORT).show();
        return;
        } else if (TextUtils.isEmpty(etPhone.getText().toString())) {
        Snackbar.make(root,"Enter Phone number first", Snackbar.LENGTH_SHORT).show();
        return;
        }else {
        opencamera(2);}
        break;
        case R.id.ADDvehical:
        if (TextUtils.isEmpty(etName.getText().toString())) {
        Snackbar.make(root,"Enter name first", Snackbar.LENGTH_SHORT).show();
        return;
        } else if (TextUtils.isEmpty(etPhone.getText().toString())) {
        Snackbar.make(root,"Enter Phone number first", Snackbar.LENGTH_SHORT).show();
        return;
        }else {
        opencamera(3);}
        break;
        case R.id.ADDlicence:
        if (TextUtils.isEmpty(etName.getText().toString())) {
        Snackbar.make(root,"Enter name first", Snackbar.LENGTH_SHORT).show();
        return;
        } else if (TextUtils.isEmpty(etPhone.getText().toString())) {
        Snackbar.make(root,"Enter Phone number first", Snackbar.LENGTH_SHORT).show();
        return;
        }else {
        opencamera(4);}
        break;
        }

        }


public void onRadioButtonClicked(View view) {
        switch (view.getId()) {
        case R.id.ecnomy:
        RideType = "ecnomy";

        premium.setChecked(false);
        bike.setChecked(false);
        truck.setChecked(false);
        drone.setChecked(false);
        van.setChecked(false);
        break;

        case R.id.premium:

        RideType = "premium";
        ecnomy.setChecked(false);
        bike.setChecked(false);
        truck.setChecked(false);
        drone.setChecked(false);
        van.setChecked(false);
        break;

        case R.id.bike:

        RideType = "bike";
        ecnomy.setChecked(false);
        premium.setChecked(false);

        truck.setChecked(false);
        drone.setChecked(false);
        van.setChecked(false);
        break;

        case R.id.van:
        RideType = "van";
        ecnomy.setChecked(false);
        premium.setChecked(false);
        bike.setChecked(false);
        truck.setChecked(false);
        drone.setChecked(false);
        break;

        case R.id.drone:
        RideType = "drone";
        ecnomy.setChecked(false);
        premium.setChecked(false);
        bike.setChecked(false);
        truck.setChecked(false);
        van.setChecked(false);
        break;

        case R.id.truck:

        RideType = "truck";
        ecnomy.setChecked(false);
        premium.setChecked(false);
        bike.setChecked(false);
        drone.setChecked(false);
        van.setChecked(false);
        }
        user1.setCarType(RideType);
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user1);
        }

public void onCheckedChanged(View view) {
        switch (view.getId()) {
        case R.id.male:
        gender = "Male";

        female.setChecked(false);
        other.setChecked(false);
        break;
        case R.id.female:

        gender = "Female";

        male.setChecked(false);
        other.setChecked(false);
        break;
        case R.id.other:

        gender = "Other";

        female.setChecked(false);
        male.setChecked(false);

        break;

        }
        }

private void opencamera(int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {

        if (data != null) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        ///ask for permissions
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), photo, "pic", null);


        uploadpic(requestCode,   Uri.parse(path));
        } else {
        Snackbar.make(root, "Failed: try again", Snackbar.LENGTH_SHORT).show();
        }


        } else {
        Snackbar.make(root, "Failed: try again", Snackbar.LENGTH_SHORT).show();
        }


        }
private void uploadpic(int chk, Uri img){

        dialog.show();
        String folder="";
        switch (chk){
        case 1:
        folder="Rider CNIC";
        //CNIC
        break;
        case 2:
        //rider oic

        folder="Rider DP";
        break;
        case 3:
        //rider vehival pic

        folder="Rider Vehical";
        break;
        case 4:
        //rider oic

        folder="Rider Licence";
        break;


        }
        Roider_phn=etPhone.getText().toString().trim();
        Roider_name=etName.getText().toString().trim();

        StorageReference REF = mStorageRef.child("Rider:"+Roider_name+", "+Roider_phn+"/"+folder+"/"+folder+".jpg");

        REF.putFile(img)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
@Override
public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        // Get a URL to the uploaded content
        REF.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                        Log.v("Hassan", "onSuccess: uri= "+ uri.toString());
                        imguri=uri.toString();
                }
        });
//         imguri = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
        switch (chk){
        case 1:
        Rider_CNIC_url=imguri;

        AddCNIC.setImageResource(R.drawable.ic_baseline_check_24);

        break;
        case 2:
        Rider_photo_url=imguri;

        ADDRIDERPIC.setImageResource(R.drawable.ic_baseline_check_24);
        break;
        case 3:

        Rider_car_photo_url=imguri;

        Addvehical.setImageResource(R.drawable.ic_baseline_check_24);
        break;

        case 4:

        Rider_licence_photo_url=imguri;

        Addlicence.setImageResource(R.drawable.ic_baseline_check_24);
        break;
        }
        if (dialog.isShowing()){
        dialog.dismiss();
        }
        }
        })
        .addOnFailureListener(new OnFailureListener() {
@Override
public void onFailure(@NonNull Exception exception) {
        Log.v("hassan","exp: "+exception);
        Snackbar.make(root, "Failed to upload: try again: "+chk, Snackbar.LENGTH_SHORT).show();
        if (dialog.isShowing()){
        dialog.dismiss();
        }
        }
        });

        }

        User user1 = new User();



private void firebaseSignup(String chk) {

        dialog.show();

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference(Common.user_driver_tbl);
        firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
@Override
public void onSuccess(AuthResult authResult) {

        user1.setEmail(Roider_email);
        user1.setPassword(Roider_pass);
        user1.setPhone(Roider_phn);
        user1.setName(Roider_name);
        user1.setCarType("UberX");


        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
        .setValue(user1)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
@Override
public void onSuccess(Void aVoid) {
        Snackbar.make(root, "Registered", Snackbar.LENGTH_SHORT).show();

        if (dialog.isShowing()){
        dialog.dismiss();
        }
        if (chk.equals("Signup")){

        currentuser = firebaseAuth.getCurrentUser();
        findViewById(R.id.profiledata).setVisibility(View.VISIBLE);
        findViewById(R.id.signupdata).setVisibility(View.GONE);

        } }

        }).addOnFailureListener(new OnFailureListener() {
@Override
public void onFailure(@NonNull Exception e) {

        if (dialog.isShowing()){
        dialog.dismiss();
        }
        Snackbar.make(root, getResources().getString(R.string.failed) + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
        });
        }

        }).addOnFailureListener(new OnFailureListener() {
@Override
public void onFailure(@NonNull Exception e) {
        Snackbar.make(root, getResources().getString(R.string.failed) + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
        });
        }
private void profilesignup(){
        dialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("RidersProfile").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        User user = new User();
        user.setEmail(Roider_email);
        user.setPassword(Roider_pass);
        user.setPhone(Roider_phn);
        user.setName(Roider_name);

        user.setGender(gender);
        user.setDOB(DOB);
        user.setYear_of_prodution(Yearofprodtution);
        user.setCarType(RideType);

        user.setRider_cnic_pic_url(Rider_CNIC_url);
        user.setRider_licence_pic(Rider_licence_photo_url);
        user.setRider_pic_Url(Rider_photo_url);
        user.setRider_vehical_pic(Rider_car_photo_url);
        myRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
@Override
public void onComplete(@NonNull Task<Void> task) {

        if (dialog.isShowing()){
        dialog.dismiss();
       new FirebaseHelper().LoadRiderProfile(getApplicationContext());
        }
        }
        });


        }
private void storagepermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 007);
                }
        }
}
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else {
                        storagepermission();
                }
        }
        }
package com.loadease.uberclone.driverapp.fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.loadease.uberclone.driverapp.Common.Common;
import com.loadease.uberclone.driverapp.R;

public class UpdateProfile extends AppCompatActivity {
    EditText etEmail, etPassword, etName, etYearofprodtution, date, mont, year,edcaarnumber;
    String DOB,Roider_phn,Roider_name, gender = "male", RideType = "economy";
    RadioButton male, female, other;
    TextView etPhone;
    LinearLayout root;
    private ProgressDialog dialog; FirebaseDatabase firebaseDatabase;
    DatabaseReference users,users_X;
    FirebaseAuth firebaseAuth;

    FirebaseUser currentuser=null; StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Validating...");
        process();
    }

    private void process() {

        root = findViewById(R.id.bg);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etYearofprodtution = findViewById(R.id.Yearofprodtution);
        date = findViewById(R.id.date);
        mont = findViewById(R.id.month);
        year = findViewById(R.id.year);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        other = findViewById(R.id.other);




        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference(Common.user_driver_tbl);
        users_X=FirebaseDatabase.getInstance().getReference("chatIntegration");

        fetchingData();

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
    public void UpdateProfile(View view) {
        Roider_name=etName.getText().toString().trim();
//        Roider_phn=etPhone.getText().toString().trim();
    if (TextUtils.isEmpty(gender)) {
            Snackbar.make(root,"select Gender", Snackbar.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(date.getText().toString()) || TextUtils.isEmpty(mont.getText().toString())|| TextUtils.isEmpty(year.getText().toString())) {
            Snackbar.make(root,"Enter Valid date of birth", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Roider_name)) {
            Snackbar.make(root,"Enter Valid Name", Snackbar.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(Roider_phn)) {
        Snackbar.make(root,"Enter Valid Phone number", Snackbar.LENGTH_SHORT).show();
        return;
    }else  {
        DOB=date.getText().toString().trim()+"-"+mont.getText().toString().trim()+"-"+year.getText().toString().trim();
        profileupdating1();
        }
    }


    private void profileupdating1(){
        dialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("RidersProfile").child(Common.userID);

        myRef.child("gender").setValue(gender);
        myRef.child("name").setValue(Roider_name);
        myRef.child("phone").setValue(Roider_phn);
        myRef.child("dob").setValue(DOB).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                profileupdating2();
            }
        });


    }
    private void profileupdating2(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("chatIntegration").child(Common.userID);

        myRef.child("name").setValue(Roider_name);
        myRef.child("phone").setValue(Roider_phn).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                profileupdating3();
            }
        });


    }
    private void profileupdating3(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DriversInformation").child(Common.userID);

        myRef.child("name").setValue(Roider_name);
        myRef.child("phone").setValue(Roider_phn).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (dialog.isShowing()){

                    dialog.dismiss();
                    Toast.makeText(UpdateProfile.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
    private void fetchingData(){
        dialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("RidersProfile").child(Common.userID);
myRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.

        switch (dataSnapshot.child("gender").getValue().toString()) {

            case "Male":
//                Toast.makeText(UpdateProfile.this, "M", Toast.LENGTH_SHORT).show();
                gender = "Male";
                female.setChecked(false);
                other.setChecked(false);
                male.setChecked(true);

                break;

            case "Female":

//                Toast.makeText(UpdateProfile.this, "F", Toast.LENGTH_SHORT).show();
                gender = "Female";
                male.setChecked(false);
                female.setChecked(true);
                other.setChecked(false);

                break;

            case "Other":

//                Toast.makeText(UpdateProfile.this, "O", Toast.LENGTH_SHORT).show();
                gender = "Other";

                other.setChecked(true);
                female.setChecked(false);
                male.setChecked(false);

                break;

        }

        DOB=dataSnapshot.child("dob").getValue().toString();

        Roider_phn=dataSnapshot.child("phone").getValue().toString();




        String[] separated = DOB.split("-");
        date.setText(separated[0]);
        mont.setText(separated[1]);
        year.setText(separated[2]);

        etName.setText(dataSnapshot.child("name").getValue().toString());
        etPhone.setText(Roider_phn);

        if (dialog.isShowing()){

            dialog.dismiss();
                 }
    }

    @Override
    public void onCancelled(DatabaseError error) {
        if (dialog.isShowing()) {

            dialog.dismiss();
        }
        // Failed to read value
    }
});

    }

}
package com.hubit.hurrydriver.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.hubit.hurrydriver.Constants.constants;
import com.hubit.hurrydriver.Models.driverProfileModel;
import com.hubit.hurrydriver.R;
import com.hubit.hurrydriver.Utils.SharedPrefManager;

public class SplashScreen extends AppCompatActivity {

    String uid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                checkUserExist();
                // throw  new RuntimeException("Test Crash") ;

            }
        }, 1000);
    }

    private void checkUserExist() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent i = new Intent(getApplicationContext(), Acitivity_Choose_SignIn_Register.class);
            startActivity(i);
            finish();
        } else {

            uid = user.getUid();
            // check user is activated or not
            DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid);
            mref.keepSynced(true);

            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        driverProfileModel model = snapshot.getValue(driverProfileModel.class);
                        Gson gson = new Gson();
                        String ProfileData = gson.toJson(model);

                        SharedPrefManager.getInstance(getApplicationContext())
                                .userLogin(ProfileData);
                        if (model.getDriverIdActivated().equals("DEACTIVATED") || model.getDriverIdActivated().equals("BAN")) {

                            // check user step 1st

                            switch (model.getRegStep()) {
                                case "1": {
                                    Intent i = new Intent(getApplicationContext(), ChooseVehicle.class);
                                    startActivity(i);
                                    finish();
                                    break;
                                }
                                case "2": {
                                    Intent i = new Intent(getApplicationContext(), remainingStepsActivity.class);
                                    startActivity(i);
                                    finish();

                                    break;
                                }
                                case "3": {

                                    Intent i = new Intent(getApplicationContext(), PickPhotoForUploadList.class);
                                    startActivity(i);
                                    finish();
                                    break;
                                }
                                case "4": {

                                    Intent i = new Intent(getApplicationContext(), DriverStatusPage.class);
                                    startActivity(i);
                                    finish();
                                    break;
                                }
                            }

                        }
                        else {
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();

                        }


                    }
                    else {
                        Toast.makeText(getApplicationContext() , "Complete Your Profile first !!" , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), signUpAcitivity.class);
                        startActivity(i);
                        finish();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Intent i = new Intent(getApplicationContext(), Acitivity_Choose_SignIn_Register.class);
                    startActivity(i);
                    finish();

                }
            });


        }

    }


}
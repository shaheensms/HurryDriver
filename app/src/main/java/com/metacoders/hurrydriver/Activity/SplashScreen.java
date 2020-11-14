package com.metacoders.hurrydriver.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurrydriver.Constants.constants;
import com.metacoders.hurrydriver.Models.driverProfileModel;
import com.metacoders.hurrydriver.R;

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

            }
        }, 700);
    }

    private void checkUserExist() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent i = new Intent(getApplicationContext(), Acitivity_Choose_SignIn_Register.class);
            startActivity(i);
            finish();
        } else {

            try {
                uid = FirebaseAuth.getInstance().getUid();

            } catch (Exception e) {

                Intent i = new Intent(getApplicationContext(), Acitivity_Choose_SignIn_Register.class);
                startActivity(i);
                finish();

            }
            if (uid == null || uid.isEmpty()) {
                Intent i = new Intent(getApplicationContext(), Acitivity_Choose_SignIn_Register.class);
                startActivity(i);
                finish();
            } else {
                // check user is activated or not

                DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid);
                mref.keepSynced(true);

                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            driverProfileModel model = snapshot.getValue(driverProfileModel.class);


                            if (model.getDriverIdActivated().equals("DEACTIVATED") || model.getDriverIdActivated().equals("BAN")) {

                                Intent i = new Intent(getApplicationContext(), DriverStatusPage.class);
                                startActivity(i);
                                finish();

                            }
                            else {
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();

                            }


                        } else {
                            Intent i = new Intent(getApplicationContext(), Acitivity_Choose_SignIn_Register.class);
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
}
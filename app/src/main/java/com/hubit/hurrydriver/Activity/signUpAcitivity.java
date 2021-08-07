package com.hubit.hurrydriver.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hubit.hurrydriver.Constants.constants;
import com.hubit.hurrydriver.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class signUpAcitivity extends AppCompatActivity {

    TextInputEditText fnamein, snameIn, emailIn, phIn, carLicNumber;
    CheckBox terms;
    Button resigterBtn;
    String fname, sname, email, phone, carLic;
    DatabaseReference mref;
    FirebaseAuth mauth;
    FirebaseUser user ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // hiding the title bar

        getSupportActionBar().hide();

        mauth = FirebaseAuth.getInstance();
        user = mauth.getCurrentUser();


        Intent o = getIntent();
        phone = o.getStringExtra("PHONE");


        // init view

        fnamein = findViewById(R.id.edt_fname);
        snameIn = findViewById(R.id.edt_lname);
        phIn = findViewById(R.id.edt_phoneNo);
        emailIn = findViewById(R.id.edt_email);
        resigterBtn = findViewById(R.id.continueBtn);
        terms = findViewById(R.id.termCheck);
        carLicNumber = findViewById(R.id.carLicName);

        phIn.setText(phone);

        terms.setChecked(false);


        //click event

        resigterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phIn.setText(phone);
                fname = fnamein.getText().toString();
                sname = snameIn.getText().toString();
                email = emailIn.getText().toString();
                //  phone = phIn.getText().toString();
                carLic = carLicNumber.getText().toString();


                if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(sname) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)
                        || phone.length() != 14 || TextUtils.isEmpty(carLic)) {

                    Toast.makeText(getApplicationContext(), "ffff" + fname + sname + email + carLic + phone, Toast.LENGTH_LONG).show();

                    new AwesomeWarningDialog(signUpAcitivity.this)
                            .setTitle("Error")
                            .setMessage(getString(R.string.fillTheDataProperly))
                            .setColoredCircle(R.color.dialogNoticeBackgroundColor)
                            .setDialogIconAndColor(R.drawable.ic_notice, R.color.white)
                            .setCancelable(true)
                            .setButtonText(getString(R.string.dialog_ok_button))
                            .setButtonBackgroundColor(R.color.dialogNoticeBackgroundColor)
                            .setButtonText(getString(R.string.dialog_ok_button))
                            .setWarningButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    // click
                                    new AwesomeErrorDialog(getApplicationContext()).hide();

                                }
                            })
                            .show();

                } else if (!terms.isChecked()) {
                    new AwesomeWarningDialog(signUpAcitivity.this)
                            .setTitle("Error")
                            .setMessage("Please Accept To Our Terms & Condition")
                            .setColoredCircle(R.color.dialogNoticeBackgroundColor)
                            .setDialogIconAndColor(R.drawable.ic_notice, R.color.white)
                            .setCancelable(true)
                            .setButtonText(getString(R.string.dialog_ok_button))
                            .setButtonBackgroundColor(R.color.dialogNoticeBackgroundColor)
                            .setButtonText(getString(R.string.dialog_ok_button))
                            .setWarningButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    // click
                                    new AwesomeErrorDialog(getApplicationContext()).hide();

                                }
                            })
                            .show();

                } else {

                    // uploading data to firebase
                    uploadDataToFirebase(fname + " " + sname, email, phone, carLic);
                }


            }
        });


    }

    private void uploadDataToFirebase(String s, String email, String phone, String carLicense) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
        Date date = new Date();
        String DATE = dateFormat.format(date);

        mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink);
        String uid = mauth.getUid();

        // TODO add  notification Subscriber


        HashMap dataMap = new HashMap();

        dataMap.put("driverName", s);
        dataMap.put("email", email);
        dataMap.put("phone", phone);
        dataMap.put("userID", uid);
        dataMap.put("driverRating", "0");
        dataMap.put("totalRides", "0");
        dataMap.put("driverFined", "0");
        dataMap.put("driverEarnedThisMonth", "0");
        dataMap.put("driverEarnedLifeLong", "0");
        dataMap.put("driverJoinedDate", DATE);
        dataMap.put("driverIdActivated", "DEACTIVATED");
        dataMap.put("carLic", carLicense);
        dataMap.put("tripCounter", "0");
        dataMap.put("regStep", "1");





        // starting to send the data to firebase

        mref.child(uid)
                .setValue(dataMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // data successfully  updated
                        Intent i = new Intent(getApplicationContext(), ChooseVehicle.class);
                        startActivity(i);
                        finish();


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                // show something  wrong
                new AwesomeWarningDialog(signUpAcitivity.this)
                        .setTitle("Error")
                        .setMessage("Error" + e.getMessage() + ". Please Try Again !!")
                        .setColoredCircle(R.color.dialogNoticeBackgroundColor)
                        .setDialogIconAndColor(R.drawable.ic_notice, R.color.white)
                        .setCancelable(false)
                        .setButtonText(getString(R.string.dialog_ok_button))
                        .setButtonBackgroundColor(R.color.dialogNoticeBackgroundColor)
                        .setButtonText(getString(R.string.dialog_ok_button))
                        .setWarningButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                // click
                                new AwesomeErrorDialog(getApplicationContext()).hide();

                            }
                        })
                        .show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Toast.makeText(getApplicationContext(), "You Cant Go Back ", Toast.LENGTH_SHORT)
                .show();

    }
}

package com.metacoders.hurrydriver.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurrydriver.Constants.constants;
import com.metacoders.hurrydriver.R;

import java.util.HashMap;

public class signUpAcitivity extends AppCompatActivity {

    TextInputEditText fnamein , snameIn, emailIn , phIn   ;
    CheckBox  terms ;
    Button resigterBtn ;
    String  fname , sname , email,  phone ;
    DatabaseReference mref ;
    FirebaseAuth mauth ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // hiding the title bar

        getSupportActionBar().hide();

        mauth = FirebaseAuth.getInstance();



        Intent o = getIntent();
        phone = o.getStringExtra("PHONE") ;


        // init view

        fnamein = findViewById(R.id.edt_fname);
        snameIn = findViewById(R.id.edt_lname);
        phIn = findViewById(R.id.edt_phoneNo);
        emailIn = findViewById(R.id.edt_email);
        resigterBtn = findViewById(R.id.continueBtn);

        phIn.setText(phone);

//        Text in
        fname = fnamein.getText().toString();
        sname = snameIn.getText().toString();
        email = emailIn.getText().toString();
        phone = phIn.getText().toString();



        //click event

        resigterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(fname) ||!TextUtils.isEmpty(sname) ||!TextUtils.isEmpty(email) ||!TextUtils.isEmpty(phone)
                        || phone.length() != 11){



                }

                else{

                    // uploading data to firebase
                    uploadDataToFirebase(fname+ " "+ sname  , email , phone) ;
                }






            }
        });





    }

    private void uploadDataToFirebase(String s, String email, String phone) {

        mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink);
        String uid  = mauth.getUid() ;


        HashMap  dataMap = new HashMap();

        dataMap.put("driverName", s) ;
        dataMap.put("email",email);
        dataMap.put("phone", phone) ;
        dataMap.put("userID" , uid) ;

        // starting to send the data to firebase

        mref.child(uid)
                .setValue(dataMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // data succefully d updated


                        Intent i = new Intent(getApplicationContext() , remainingStepsActivity.class);

                        startActivity(i);



                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                // show something  wrong
                new AwesomeWarningDialog(signUpAcitivity.this)
                        .setTitle("Error")
                        .setMessage("Error"+ e.getMessage()+ ". Please Try Again !!")
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
                                new AwesomeErrorDialog(getApplicationContext()).hide() ;

                            }
                        })
                        .show();

            }
        });




    }
}

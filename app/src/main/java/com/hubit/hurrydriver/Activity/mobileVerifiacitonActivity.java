package com.hubit.hurrydriver.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hubit.hurrydriver.Constants.constants;
import com.hubit.hurrydriver.Models.driverProfileModel;
import com.hubit.hurrydriver.R;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;
import ir.samanjafari.easycountdowntimer.CountDownInterface;
import ir.samanjafari.easycountdowntimer.EasyCountDownTextview;

public class mobileVerifiacitonActivity extends AppCompatActivity {

    EasyCountDownTextview countDownTextView;
    TextView resendText;
    String phone;
    ImageButton backButton;
    FirebaseAuth.AuthStateListener mAuthListener;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    ImageButton signInBtn;
    FirebaseUser mUser;
    String State;
    private String verificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private OtpTextView editText;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

//
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
//            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            verificationid = s;
//        }
//
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//            String code = phoneAuthCredential.getSmsCode();
//            if (code != null) {
//                verifyCode(code);
//            } else {
//                progressBar.setVisibility(View.INVISIBLE);
//
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//            progressBar.setVisibility(View.INVISIBLE);
//            Toast.makeText(mobileVerifiacitonActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//
//        }
//    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verifiaciton);

        getSupportActionBar().hide();
        //receiving  phone number from  the previous activity

        // [START phone_auth_callbacks]

        // [END phone_auth_callbacks]


    // [START on_start_check_user]
        Intent o = getIntent();
        phone = o.getStringExtra("PHONE");
        State = o.getStringExtra("STATe");


        //init view
        countDownTextView = findViewById(R.id.easyCountDownTextview);
        resendText = findViewById(R.id.resendTv);
        backButton = findViewById(R.id.backbtn);
        signInBtn = findViewById(R.id.signin_btn);
        editText = findViewById(R.id.otp_view);
        progressBar = findViewById(R.id.progrssBar);

        mAuth = FirebaseAuth.getInstance();
        //setting my views
        countDownTextView.setVisibility(View.VISIBLE);
        resendText.setVisibility(View.GONE);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("TAG", "onVerificationCompleted:" + credential);

                String code = credential.getSmsCode();

                if (code != null) {
                    editText.setOTP(code);
                    verifyCode(code);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);

                }
                signInWithCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed", e);
                progressBar.setVisibility(View.INVISIBLE);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(mobileVerifiacitonActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(mobileVerifiacitonActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                else   Toast.makeText(mobileVerifiacitonActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };


        countDownTextView.setOnTick(new CountDownInterface() {
            @Override
            public void onTick(long time) {

            }

            @Override
            public void onFinish() {

                countDownTextView.setVisibility(View.GONE);
                resendText.setVisibility(View.VISIBLE);


            }
        });


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String code = editText.getOTP();

                if ((code.isEmpty() || code.length() < 6)) {

                    //  editText.setError("Enter code...");
                    Toast.makeText(getApplicationContext(), "PLease Enter The 6 Digit Code Properly", Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    // progressBar.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    verifyCode(code);
                }


            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        });

        resendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendText.setVisibility(View.GONE);
                countDownTextView.setVisibility(View.VISIBLE);
                sendVerificationCode(phone);
                countDownTextView.startTimer();
            }
        });

        sendVerificationCode(phone);
    }

    private void verifyCode(String code) {
        Log.d("TAG", "verifyCode: " +  code);
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithCredential(credential);
        } catch (Exception e) {
            progressBar.setVisibility(View.INVISIBLE);
            Log.i("Error : ", " " + e.getMessage());
            Toast toast = Toast.makeText(this, "Error   " + e.getMessage(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }




    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        mAuth = FirebaseAuth.getInstance();

                        checkIfUserExists(mAuth.getUid());

                    } else {

                        Toast.makeText(mobileVerifiacitonActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void sendVerificationCode(String number) {

        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }

    private void checkIfUserExists(String uid) {
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid);
        mref.keepSynced(true);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // user exist
                    driverProfileModel model = snapshot.getValue(driverProfileModel.class);
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

                            Intent intent = new Intent(mobileVerifiacitonActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    }


                } else {
                    // user doesnt exist
                    Intent intent = new Intent(mobileVerifiacitonActivity.this, signUpAcitivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("PHONE", phone);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error " + error, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//
//            Intent intent = new Intent(mobileVerifiacitonActivity.this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }
}

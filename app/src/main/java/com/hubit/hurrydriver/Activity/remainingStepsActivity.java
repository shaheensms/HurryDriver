package com.hubit.hurrydriver.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hubit.hurrydriver.Constants.constants;
import com.hubit.hurrydriver.R;

public class remainingStepsActivity extends AppCompatActivity {

    private static final int INTENT_DRIVERLICENCE = 100;
    private static final int INTENT_NIDCARD = 200;
    private static final int INTENT_PPUPLOAD = 300;
    private static final int INTENT_FITNESS = 400;
    private static final int INTENT_VEHICLE_REG_IMAGE = 600;
    private static final int INTENT_TAXTOKEN = 500;

    CardView driverLicenceBtn, nidCardBtn, ppuploadBtn, fitnessBtn, taxToken, vehicleRegistrationBtn;
    Button  nextButton ;


    Boolean dirverLicenceClick = false, nidCardClick = false,
            ppUplaodClick = false, fitnessClick = false,
            taxClick = false, vehicleRegClick = false;

    String state = "";

    Intent o;

    ImageView   driverLicIcon  ,  nidICon , ppIcon , fitnessIcon , taxTokenIcon , vehicleREgIcon ;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remaining_steps);

        getSupportActionBar().hide();

        // init views
        driverLicenceBtn = findViewById(R.id.driverLicenseUploadBtn);
        nidCardBtn = findViewById(R.id.nidUploadBtn);
        ppuploadBtn = findViewById(R.id.profileUploadBtn);
        fitnessBtn = findViewById(R.id.vehicleCertificateUploadBtn);
        taxToken = findViewById(R.id.taxTokenUploadBtn);
        vehicleRegistrationBtn = findViewById(R.id.RegistrationImageUploadBtn);
        driverLicIcon = findViewById(R.id.driverIcon);
        nidICon = findViewById(R.id.nidIcon);
        ppIcon = findViewById(R.id.ppicon) ;
        fitnessIcon = findViewById(R.id.fitnessIcon) ;
        taxTokenIcon = findViewById(R.id.taxtokenIcon) ;
        vehicleREgIcon = findViewById(R.id.regPaperIcon);
        nextButton = findViewById(R.id.nextStepButton);

        // setting tag
        vehicleREgIcon.setTag("NOTOK");
        taxTokenIcon.setTag("NOTOK");
        fitnessIcon.setTag("NOTOK");
        ppIcon.setTag("NOTOK");
        nidICon.setTag("NOTOK");
        driverLicIcon.setTag("NOTOK");




        // setting on click listenner

        o = new Intent(getApplicationContext(), Driver_Licence_upload_Activity.class);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if all the work is done or not
                if(vehicleREgIcon.getTag()=="NOTOK"){
                    // show something  wrong
                    new AwesomeWarningDialog(remainingStepsActivity.this)
                            .setTitle("Error")
                            .setMessage(getString(R.string.upload_vehicle_reg_papaer))
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
                else if (taxTokenIcon.getTag()=="NOTOK"){

                    new AwesomeWarningDialog(remainingStepsActivity.this)
                            .setTitle("Error")
                            .setMessage(getString(R.string.upload_tax_token))
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
                else if (fitnessIcon.getTag()=="NOTOK"){
                    new AwesomeWarningDialog(remainingStepsActivity.this)
                            .setTitle("Error")
                            .setMessage(getString(R.string.upload_vehicle_fitness_paper)+"")
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
                else if (ppIcon.getTag()=="NOTOK"){

                    new AwesomeWarningDialog(remainingStepsActivity.this)
                            .setTitle("Error")
                            .setMessage(getString(R.string.upload_your_profile_pic)+ "")
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
                else if (nidICon.getTag()=="NOTOK"){
                    new AwesomeWarningDialog(remainingStepsActivity.this)
                            .setTitle("Error")
                            .setMessage("" + getString(R.string.ulpoad_your_nid))
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

                else if (driverLicIcon.getTag()=="NOTOK"){

                    new AwesomeWarningDialog(remainingStepsActivity.this)
                            .setTitle("Error")
                            .setMessage(""+ getString(R.string.upload_dirver_lic_image))
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
                else {

                   // add the step number
                    FirebaseAuth mauth = FirebaseAuth.getInstance();
                    DatabaseReference databaseReference = FirebaseDatabase
                            .getInstance()
                            .getReference(constants.driverProfileLink).child(mauth.getUid()).child("regStep");

                    databaseReference.setValue("3")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(getApplicationContext() , PickPhotoForUploadList.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Try Again Error : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });



                }





            }
        });


        driverLicenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                state = "driverLicence";

                o.putExtra("STATE", state);

                startActivityForResult(o , INTENT_DRIVERLICENCE);

            }
        });

        nidCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                state = "nidCard";

                o.putExtra("STATE", state);

                startActivityForResult(o , INTENT_NIDCARD);


            }
        });

        ppuploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = "ppUpload";
                o.putExtra("STATE", state);

                startActivityForResult(o , INTENT_PPUPLOAD);


            }
        });

        fitnessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                state = "fitness";

                o.putExtra("STATE", state);

                startActivityForResult(o , INTENT_FITNESS);


            }
        });

        taxToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                state = "taxToken";
                o.putExtra("STATE", state);

                startActivityForResult(o , INTENT_TAXTOKEN);


            }
        });
        vehicleRegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                state = "vehicleRegImage";
                o.putExtra("STATE", state);
                startActivityForResult(o , INTENT_VEHICLE_REG_IMAGE);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
   //     ImageView   driverLicIcon  ,  nidICon , ppIcon , fitnessIcon , taxTokenIcon , vehicleREgIcon ;

        if (requestCode == INTENT_DRIVERLICENCE && resultCode == RESULT_OK) {

            driverLicIcon.setImageResource(R.drawable.done_icon);
            driverLicIcon.setTag("OK");

        } else if (requestCode == INTENT_NIDCARD && resultCode == RESULT_OK) {

            nidICon.setImageResource(R.drawable.done_icon);
            nidICon.setTag("OK");

        } else if (requestCode == INTENT_PPUPLOAD && resultCode == RESULT_OK) {

            ppIcon.setImageResource(R.drawable.done_icon);
            ppIcon.setTag("OK");



        } else if (requestCode == INTENT_FITNESS && resultCode == RESULT_OK) {

            fitnessIcon.setImageResource(R.drawable.done_icon);
            fitnessIcon.setTag("OK");
        } else if (requestCode == INTENT_TAXTOKEN && resultCode == RESULT_OK) {
                taxTokenIcon.setImageResource(R.drawable.done_icon);
                taxTokenIcon.setTag("OK");

        } else if (requestCode == INTENT_VEHICLE_REG_IMAGE && resultCode == RESULT_OK) {

            vehicleREgIcon.setImageResource(R.drawable.done_icon);
            vehicleREgIcon.setTag("OK");

        } else {
            /*
            driverLicIcon.setImageResource(R.drawable.incomplete_icon);
            nidICon.setImageResource(R.drawable.incomplete_icon);
            ppIcon.setImageResource(R.drawable.incomplete_icon);
            fitnessIcon.setImageResource(R.drawable.incomplete_icon);
            taxTokenIcon.setImageResource(R.drawable.incomplete_icon);
            vehicleREgIcon.setImageResource(R.drawable.incomplete_icon);

             */
        }


    }
}

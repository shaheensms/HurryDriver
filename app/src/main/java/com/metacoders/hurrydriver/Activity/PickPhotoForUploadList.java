package com.metacoders.hurrydriver.Activity;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurrydriver.Constants.constants;
import com.metacoders.hurrydriver.R;

public class PickPhotoForUploadList extends AppCompatActivity {
    CardView frontBtn, backBtn, sideBtn, insideBtn;
    Button nextButton ;
    private static final int INTENT_FRONT = 100;
    private static final int INTENT_BACK = 200;
    private static final int INTENT_SIDE= 300;
    private static final int INTENT_INSIDE = 400;

    String state = "" , uid ;

    Intent o;

    ImageView front  ,  back , side , inside  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_photo_upload);
        getSupportActionBar().hide();

        // uid
        uid = FirebaseAuth.getInstance().getUid() ;


        // init views
        frontBtn = findViewById(R.id.frontUploadBtn);
        backBtn = findViewById(R.id.backUploadBtn);
        sideBtn = findViewById(R.id.sideUploadBtn);
        insideBtn = findViewById(R.id.insideUploadBtn);

        // icons
        front = findViewById(R.id.driverfrontIcon);
        inside = findViewById(R.id.regPaperIconinside) ;
        back = findViewById(R.id.fitnessIconBack) ;
        side = findViewById(R.id.ppiconside);
        nextButton = findViewById(R.id.nextStepButton);

        ///seting tags
        front.setTag("NOTOK");
        inside.setTag("NOTOK");
        back.setTag("NOTOK");
        side.setTag("NOTOK");

        // setting on click listenner

        o = new Intent(getApplicationContext(), uploadCarPicToServer.class);

        frontBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                state = "front";

                o.putExtra("STATE", state);

                startActivityForResult(o , INTENT_FRONT);


            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                state = "back";

                o.putExtra("STATE", state);

                startActivityForResult(o , INTENT_BACK);


            }
        });

        sideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = "side";
                o.putExtra("STATE", state);

                startActivityForResult(o , INTENT_SIDE);


            }
        });

        insideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                state = "inside";

                o.putExtra("STATE", state);

                startActivityForResult(o , INTENT_INSIDE);


            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // check if all the work is done or not
                if(front.getTag()=="NOTOK"){
                    // show something  wrong
                    new AwesomeWarningDialog(PickPhotoForUploadList.this)
                            .setTitle("Error")
                            .setMessage("Please Upload the Front Side Image Of Your Car")
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
                else if (side.getTag()=="NOTOK"){

                    new AwesomeWarningDialog(PickPhotoForUploadList.this)
                            .setTitle("Error")
                            .setMessage("Please Upload The  Side Image Of Your Car")
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
                else if (inside.getTag()=="NOTOK"){
                    new AwesomeWarningDialog(PickPhotoForUploadList.this)
                            .setTitle("Error")
                            .setMessage("Please Upload the Image Of Inside Of Your Car")
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
                else if (back.getTag()=="NOTOK"){

                    new AwesomeWarningDialog(PickPhotoForUploadList.this)
                            .setTitle("Error")
                            .setMessage("Please Upload the Back Side Image Of Your Car")
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
                    

//                    DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid);
//                    mref.child("driverIdActivated").setValue() ;


                    

                    Intent intent = new Intent(getApplicationContext() , DriverStatusPage.class);
                    startActivity(intent);
                    finish();

                }

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //     ImageView   driverLicIcon  ,  nidICon , ppIcon , fitnessIcon , taxTokenIcon , vehicleREgIcon ;

        if (requestCode == INTENT_FRONT && resultCode == RESULT_OK) {

            front.setImageResource(R.drawable.done_icon);
            front.setTag("OK");

        } else if (requestCode == INTENT_BACK && resultCode == RESULT_OK) {

            back.setImageResource(R.drawable.done_icon);
            back.setTag("OK");

        } else if (requestCode == INTENT_INSIDE && resultCode == RESULT_OK) {

            inside.setImageResource(R.drawable.done_icon);
            inside.setTag("OK");



        } else if (requestCode == INTENT_SIDE && resultCode == RESULT_OK) {

            side.setImageResource(R.drawable.done_icon);
            side.setTag("OK");
        }
        else {
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

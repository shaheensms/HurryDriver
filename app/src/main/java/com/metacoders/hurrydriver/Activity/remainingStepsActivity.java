package com.metacoders.hurrydriver.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.metacoders.hurrydriver.R;

public class remainingStepsActivity extends AppCompatActivity {

    private static final int INTENT_DRIVERLICENCE = 100;
    private static final int INTENT_NIDCARD = 200;
    private static final int INTENT_PPUPLOAD = 300;
    private static final int INTENT_FITNESS = 400;
    private static final int INTENT_VEHICLE_REG_IMAGE = 600;
    private static final int INTENT_TAXTOKEN = 500;

    CardView driverLicenceBtn, nidCardBtn, ppuploadBtn, fitnessBtn, taxToken, vehicleRegistrationBtn;

    Boolean dirverLicenceClick = false, nidCardClick = false,
            ppUplaodClick = false, fitnessClick = false,
            taxClick = false, vehicleRegClick = false;

    String state = "";

    Intent o;

    ImageView   driverLicIcon  ,  nidICon , ppIcon , fitnessIcon , taxTokenIcon , vehicleREgIcon ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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




        // setting on click listenner

        o = new Intent(getApplicationContext(), Driver_Licence_upload_Activity.class);


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

        } else if (requestCode == INTENT_NIDCARD && resultCode == RESULT_OK) {

            nidICon.setImageResource(R.drawable.done_icon);

        } else if (requestCode == INTENT_PPUPLOAD && resultCode == RESULT_OK) {

            ppIcon.setImageResource(R.drawable.done_icon);


        } else if (requestCode == INTENT_FITNESS && resultCode == RESULT_OK) {

            fitnessIcon.setImageResource(R.drawable.done_icon);
        } else if (requestCode == INTENT_TAXTOKEN && resultCode == RESULT_OK) {
                taxTokenIcon.setImageResource(R.drawable.done_icon);

        } else if (requestCode == INTENT_VEHICLE_REG_IMAGE && resultCode == RESULT_OK) {

            vehicleREgIcon.setImageResource(R.drawable.done_icon);

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
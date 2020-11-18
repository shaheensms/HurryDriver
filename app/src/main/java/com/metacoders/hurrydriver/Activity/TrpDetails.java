package com.metacoders.hurrydriver.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.metacoders.hurrydriver.Models.modelForCarRequest;
import com.metacoders.hurrydriver.R;
import com.shuhart.stepview.StepView;

public class TrpDetails extends AppCompatActivity {

    String driverName, carModel, fromLoc, toLoc, fare, time, postID, driverID, driverNottificationID,
            tripDetails, status, triptype, driverFine, driverTotalTrip = "0", driverIncome = "0", driverTripCountThisMOn = "0",
            driverLifeTimeIncome = "0";
    EditText description;
    String userTotalTrip = "0", userFined, userSpent = "0";

    Button submit, cancel;
    MaterialCardView payInfoCard, customerCard;
    RatingBar mRateBar;
    String uid = "TEST";
    TextView DriverNAME, CARMODEL, drivername, FROMLOC, TOLOC, FARE, TIME, POSTID, DRIVERID, DRIVERNOTTIFICATIONID, descTv, typeTv, fareTv;
    StepView stepView;
    String driverNewLifetimeEarn, driverNewThisMonthEarn;
    modelForCarRequest requestModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trp_details);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + "Trip Details" + "</font>"));


        Intent i = getIntent();
        driverName = i.getStringExtra("DRIVERNAME");
        carModel = i.getStringExtra("CARMODEL");
        fromLoc = i.getStringExtra("FORMLOC");
        status = i.getStringExtra("STATUS");
        toLoc = i.getStringExtra("TOLOC");
        fare = i.getStringExtra("FARE");
        time = i.getStringExtra("TIME");
        postID = i.getStringExtra("POSTID");
        driverID = i.getStringExtra("DRIVERUID");
        driverNottificationID = i.getStringExtra("DRIVERNOTIFICATIONID");
        tripDetails = i.getStringExtra("DESC");
        triptype = i.getStringExtra("TYPE");
        requestModel = (modelForCarRequest) i.getSerializableExtra("MODEL");


        //init The View ;
        drivername = findViewById(R.id.driverNameinTripDetails);
        //   DriverNAME =findViewById(R.id.driverNameTripDeatils)  ;
        //  CARMODEL=findViewById(R.id.carModelTripDetails)  ;
        FROMLOC = findViewById(R.id.locationFromTripDetails);
        TOLOC = findViewById(R.id.locationToTripDetalis);
        FARE = findViewById(R.id.priceViewInTripDetails);
        TIME = findViewById(R.id.dateOfTripDetails);
        description = findViewById(R.id.feedBackDetails);
        submit = findViewById(R.id.sumbutBTnTripdetails);
        stepView = findViewById(R.id.step_view);
        cancel = findViewById(R.id.cancelBTnTripdetails);
        payInfoCard = findViewById(R.id.advacePayInfoCard);
        customerCard = findViewById(R.id.driverInfoCard);
        mRateBar = findViewById(R.id.ratingBarBidTripDetails);
        fareTv = findViewById(R.id.fareTv);
        typeTv = findViewById(R.id.typeTv);
        descTv = findViewById(R.id.descTv);

        fareTv.setText("৳" + requestModel.getFare());
        FARE.setText(requestModel.getFare());

        if (status.equals("Driver Found")) {
            //TODO Bring The Advance Payment App Tab
            payInfoCard.setVisibility(View.VISIBLE);
            stepView.setVisibility(View.VISIBLE);
            customerCard.setVisibility(View.GONE);
        } else if (status.toLowerCase().equals("pending")) {
            payInfoCard.setVisibility(View.GONE);
            stepView.setVisibility(View.VISIBLE);
            customerCard.setVisibility(View.GONE);
        } else {
            //TODO Show Dirver Details
            customerCard.setVisibility(View.VISIBLE);
            payInfoCard.setVisibility(View.GONE);
            stepView.setVisibility(View.VISIBLE);


        }
        // setting the step view  listeners

        stepView.getState()
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();

        setAnimationtoStepVIews();

    }

    public void setAnimationtoStepVIews() {
        customerCard.setVisibility(View.GONE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stepView.go(0, true);
            }
        }, 400);

        final Handler handsler = new Handler();
        handsler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stepView.go(1, true);
            }
        }, 800);
        payInfoCard.setVisibility(View.VISIBLE);
        if (requestModel.getStatus().toLowerCase().equals("driver found")) {
            final Handler handdsler = new Handler();
            handdsler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stepView.go(2, true);
                }
            }, 1200);
            customerCard.setVisibility(View.GONE);



        }

        if (requestModel.getStatus().toLowerCase().equals("accepted") && requestModel.getTransId().toLowerCase().equals("null")) {
            final Handler handdsler = new Handler();
            handdsler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stepView.go(2, true);
                }
            }, 1200);
            customerCard.setVisibility(View.GONE);
            payInfoCard.setVisibility(View.VISIBLE);

        }
        // check if trx gone or not
        if (requestModel.getStatus().toLowerCase().equals("accepted") && !requestModel.getTransId().toLowerCase().equals("null")) {
            final Handler handdsler = new Handler();
            handdsler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stepView.go(3, true);
                }
            }, 1200);
            customerCard.setVisibility(View.VISIBLE);
            payInfoCard.setVisibility(View.GONE);
        }


    }
}
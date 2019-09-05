package com.metacoders.hurrydriver.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurrydriver.Constants.constants;
import com.metacoders.hurrydriver.R;

import java.util.HashMap;

public class Activity_Bid_Page_Driver extends AppCompatActivity {

    CardView truck , car  , ambulance ;

    String drivername  , status , postid , usernottificationid ,timeoftrip,fromloc,toloc,numberofppl , desc , returndate , ridetype ;
    TextView timeOfTheTripTv , fromlocTv , tolocTv , numberofpplTv , descTv , rideTypeTv ;
    Button  bidSubmitBtn ;
    EditText bidInput ;
    String bid , uid  ;
    HashMap map ;

    DatabaseReference mref, driverRef  ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bid_page);


        getSupportActionBar().setTitle("Place Your Bid");


        // recive the data  form prev activity
            Intent o = getIntent();
            drivername = o.getStringExtra("drivername") ;
            status = o.getStringExtra("status") ;
            postid = o.getStringExtra("postid") ;
            usernottificationid = o.getStringExtra("usernottificationid") ;
            timeoftrip = o.getStringExtra("timeoftrip") ;
            fromloc = o.getStringExtra("fromloc");
            toloc = o.getStringExtra("toloc");
            numberofppl= o.getStringExtra("numberofppl");
            desc = o.getStringExtra("des");
            returndate = o.getStringExtra("returndate") ;
            ridetype = o.getStringExtra("ridetype") ;



             //init views
        timeOfTheTripTv = findViewById(R.id.dateOfRow);
        fromlocTv = findViewById(R.id.locationFrom);
        tolocTv =  findViewById(R.id.locationTo);
        numberofpplTv= findViewById(R.id.numberOfPPlTv);
        descTv = findViewById(R.id.desc);
        rideTypeTv = findViewById(R.id.rideTypeTv);
        bidInput = findViewById(R.id.bidPrice);
        bidSubmitBtn = findViewById(R.id.submitBtn);


        //setting text to the view
        timeOfTheTripTv.setText(timeoftrip);
        fromlocTv.setText(fromloc);
        tolocTv.setText(toloc);
        numberofpplTv.setText(numberofppl);
        descTv.setText(desc);
        rideTypeTv.setText(ridetype);


        //sumbit button function

        bidSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the bid
                bid = bidInput.getText().toString() ;

                addthebidToServer(bid);





            }
        });





    }

    private void addthebidToServer(String bid) {

        //TODO seting the uid to test for test purpose

        uid = "TEST" ;

        mref = FirebaseDatabase.getInstance().getReference(constants.carRequestLink).child(postid).child("bids").child(uid);

        driverRef = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid).child(constants.driverBidDir);
        // String     , driverRating  , bidPrice   ,driverImageLink;

         map = new HashMap();
        map.put("driverUid" ,uid  );
        map.put("bidPrice", bid) ;
        map.put("driverNottificationId" ,"test" ) ;
        map.put("userNotticationId" , usernottificationid) ;
        map.put("tripId", postid) ;
        map.put("postID", postid) ;
        map.put("driverName", "DriverNamePlaceHolder") ;
        map.put("drivercarcondition", "drivercarconditionPlaceHolder");
        map.put("driverCarModel", "driverCarModels") ;
        map.put("driverRating", "9") ;
        map.put("driverImageLink", "ImageLink") ;



        mref.setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    //uploading data to driver db

                        driverRef.child(postid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                finish();


                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(getApplicationContext(), "Error : "+ e.getMessage() , Toast.LENGTH_LONG)
                                                .show();


                                    }
                                }) ;





                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(), "Error : "+ e.getMessage() , Toast.LENGTH_LONG)
                                .show();


                    }
                }) ;


    }
}

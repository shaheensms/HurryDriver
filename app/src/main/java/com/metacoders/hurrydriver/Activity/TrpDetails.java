package com.metacoders.hurrydriver.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurrydriver.Constants.constants;
import com.metacoders.hurrydriver.Models.DriverWalletModel;
import com.metacoders.hurrydriver.Models.TransactionsModel;
import com.metacoders.hurrydriver.Models.modelForCarRequest;
import com.metacoders.hurrydriver.Models.userModel;
import com.metacoders.hurrydriver.R;
import com.shuhart.stepview.StepView;

import org.joda.time.DateTimeComparator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrpDetails extends AppCompatActivity {

    String driverName, carModel, fromLoc, toLoc, fare, time, postID, driverID, driverNottificationID,
            tripDetails, status, triptype, driverFine, driverTotalTrip = "0", driverIncome = "0", driverTripCountThisMOn = "0",
            driverLifeTimeIncome = "0";
    EditText description;
    String userTotalTrip = "0", userFined, userSpent = "0";

    Button submit, cancel, completeBtn;
    MaterialCardView payInfoCard, customerCard;
    RatingBar mRateBar;
    TextView drivername,advancePayment,userPhone, FROMLOC, TOLOC, FARE, TIME, POSTID, DRIVERID, DRIVERNOTTIFICATIONID, descTv, typeTv, fareTv;
    StepView stepView;
    String driverNewLifetimeEarn, driverNewThisMonthEarn;
    modelForCarRequest requestModel = null;
    CircleImageView circleImageView;

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
        circleImageView = findViewById(R.id.pp);


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
        cancel.setVisibility(View.GONE);
        userPhone = findViewById(R.id.phoneNumINTripDetails);
        completeBtn = findViewById(R.id.completeBTnTripdetails);
        completeBtn.setVisibility(View.GONE);
        advancePayment =  findViewById(R.id.advancedpriceInTripDetails) ;
        fareTv.setText("৳" + requestModel.getFare());
        FARE.setText(requestModel.getFare());



        if (status.toLowerCase().equals("accepted") && !requestModel.getTransId().toLowerCase().equals("null")) {
            payInfoCard.setVisibility(View.GONE);
            stepView.setVisibility(View.GONE);
            customerCard.setVisibility(View.VISIBLE);
            loadTheAdvancePayment(requestModel.getPostId());
        } else if (status.equals("Driver Found")) {
            payInfoCard.setVisibility(View.VISIBLE);
            stepView.setVisibility(View.VISIBLE);
            customerCard.setVisibility(View.GONE);
        } else if (status.toLowerCase().equals("pending")) {
            payInfoCard.setVisibility(View.GONE);
            stepView.setVisibility(View.VISIBLE);
            customerCard.setVisibility(View.GONE);
        } else if (status.toLowerCase().equals("trans")) {
            customerCard.setVisibility(View.VISIBLE);
            payInfoCard.setVisibility(View.GONE);
            stepView.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
        } else {

            customerCard.setVisibility(View.GONE);
            payInfoCard.setVisibility(View.VISIBLE);
            stepView.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
        }
        // setting the step view  listeners
        stepView.getState()
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();

        setAnimationtoStepVIews();
        setData();
        downloadClientsData();


        completeBtn.setOnClickListener(v -> {
            ProgressDialog dialog = new ProgressDialog(TrpDetails.this);
            dialog.setMessage("Completing Trip ...");
            dialog.setCancelable(false);
            dialog.show();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(new Date()); //2016/11/16 12:08:43
            //1st Upload It To The Driver profile

            DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(driverID)
                    .child(constants.succfulllistDir);

            final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(constants.clientProfile).child(requestModel.getUserId())
                    .child(constants.succfulllistDir);

            final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference(constants.carRequestLink).child(requestModel.getPostId());

            postRef.child("status").setValue("Completed") ;

            final HashMap<String, Object> map = new HashMap<>();
            map.put("tripID", postID);
            map.put("status", "Completed");
            map.put("fromLocation", fromLoc);
            map.put("toLocation", toLoc);
            map.put("fareGained", fare);
            map.put("fare" , requestModel.getFare()) ;
            map.put("CompleteDate", date);
            driverRef.child(postID).setValue(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            // Writing in user dir
                            userRef.child(postID).updateChildren(map)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            CreateDriverWallet();
                                            DatabaseReference mreff = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(driverID);

                                            final Map<String, Object> updates = new HashMap<String, Object>();
                                            updates.put("driverEarnedLifeLong", "0");
                                            updates.put("driverEarnedThisMonth", "0");
                                            updates.put("totalRides", driverTotalTrip);
                                            updates.put("tripCounter", driverTripCountThisMOn);


                                            mreff.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    DatabaseReference updateUserref = FirebaseDatabase.getInstance().getReference(constants.clientProfile).child(requestModel.getUserId());
//                                                    Map<String, Object> updatesuserData = new HashMap<String, Object>();
//
//                                                    updatesuserData.put("userTotalSpent", userSpent);
//                                                    updatesuserData.put("userTripCount", userTotalTrip);


                                                    dialog.dismiss();

                                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT)
                                                            .show();
                                                    finish();


                                                }
                                            })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            dialog.dismiss();
                                                            Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();


                                                        }
                                                    });


                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            dialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    private void loadTheAdvancePayment(String postId) {
      DatabaseReference  mref = FirebaseDatabase.getInstance().getReference("transaction_List").child(postId);
        mref.keepSynced(true);
      mref.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              if(snapshot.exists()){
                  TransactionsModel model = snapshot.getValue(TransactionsModel.class) ;
               String amountPaidstr =   model.getAmountPaid() ;
               Float amountPaid  = Float.valueOf(amountPaidstr) ;
               fare = (Integer.parseInt(fare) - amountPaid)+"" ;
               advancePayment.setText(amountPaid+"");
               FARE.setText(fare);


              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

    }

    void setData() {
        //    DriverNAME.setText(driverName);
        drivername.setText(driverName);
        // CARMODEL.setText(carModel);
        FROMLOC.setText(fromLoc);
        TOLOC.setText(toLoc);
        TIME.setText(time);
        FARE.setText(fare);
        descTv.setText(tripDetails);
        fareTv.setText(fare);
        if (requestModel.getRideType().toLowerCase().contains("hourly")) {

            typeTv.setText("Hourly " + requestModel.getHour_time());
        } else {
            typeTv.setText(triptype);
        }
    }

    public void setAnimationtoStepVIews() {
        //  customerCard.setVisibility(View.GONE);

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
        // payInfoCard.setVisibility(View.VISIBLE);
        if (requestModel.getStatus().toLowerCase().equals("driver found")) {
            final Handler handdsler = new Handler();
            handdsler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stepView.go(2, true);
                }
            }, 1200);
            //   customerCard.setVisibility(View.GONE);


        }

        if (requestModel.getStatus().toLowerCase().equals("accepted") && requestModel.getTransId().toLowerCase().equals("null")) {
            final Handler handdsler = new Handler();
            handdsler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stepView.go(2, true);
                }
            }, 1200);
            //   customerCard.setVisibility(View.GONE);
            //   payInfoCard.setVisibility(View.VISIBLE);

        }
        // check if trx gone or not
        if (requestModel.getStatus().toLowerCase().equals("accepted") && !requestModel.getTransId().toLowerCase().equals("null")) {

            SetCanclOrCompletedButton();
           final Handler handdsler = new Handler();
            handdsler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stepView.go(3, true);

                }
            }, 1200);
            //  customerCard.setVisibility(View.VISIBLE);
            //  payInfoCard.setVisibility(View.GONE);
        }


    }

    public void downloadClientsData() {
        // Download Driver Data
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.clientProfile).child(requestModel.getUserId());
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                userModel model = dataSnapshot.getValue(userModel.class);
                drivername.setText(model.getUserName());
                Glide.with(getApplicationContext())
                        .load(model.getUserProPic())
                        .into(circleImageView);

                userPhone.setText(model.getPhone());


                // driverTripCountThisMOn = String.valueOf(Integer.valueOf(driverTripCountThisMOn) + 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), "Error" + databaseError.getMessage(), Toast.LENGTH_SHORT)
                        .show();

            }
        });


    }

    public void SetCanclOrCompletedButton() {
        DateTimeComparator comparator = DateTimeComparator.getDateOnlyInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Date startDate = new Date();
        try {
            startDate = sdf.parse(requestModel.getReqDate());
            date = new Date(System.currentTimeMillis()); //16/11/2020
        } catch (ParseException e) {
            e.printStackTrace();
            //       date2 = new Date(System.currentTimeMillis());
        }

        int retVal = comparator.compare(startDate, date);
    //    Toast.makeText(getApplicationContext() , "" + retVal+" " + startDate+ " " + date , Toast.LENGTH_LONG).show();
        Log.d("TAG", "SetCanclOrCompletedButton: "+ "" + retVal+" " + startDate+ " " + date);

        if (retVal == 0) {
            //both dates are equal
            completeBtn.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.INVISIBLE);

        } else if (retVal < 0) {
            //myDateOne is before myDateTwo
            // start day  is smaller

            completeBtn.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);


        } else if (retVal > 0) {
            //myDateOne is after myDateTwo
            // start day is bigger
            completeBtn.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);

        }

        Log.d("TAG", "onCliccck: " + retVal);
    }

    private void CreateDriverWallet() {
        double totalAmount = Double.parseDouble(FARE.getText().toString());
        // now register it to the servers
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("wallet").child(driverID);
        String key = mref.push().getKey() ;

        DriverWalletModel model = new DriverWalletModel(System.currentTimeMillis()+"" ,requestModel.getPostId(), driverID , totalAmount , true  ) ;
        //

        mref.child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
            }
        }) ;

    }

}
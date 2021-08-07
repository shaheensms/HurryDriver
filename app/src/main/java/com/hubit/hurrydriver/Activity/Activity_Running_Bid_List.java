package com.hubit.hurrydriver.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hubit.hurrydriver.Constants.constants;
import com.hubit.hurrydriver.Models.modelForCarRequest;
import com.hubit.hurrydriver.R;
import com.hubit.hurrydriver.Viewholders.viewHolderForRunningBId;
import com.hubit.hurrydriver.Viewholders.viewHolderForRunningTrip;

public class Activity_Running_Bid_List extends AppCompatActivity {

    RecyclerView mrecyclerview;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference mref, checkRef, reqRef;

    FirebaseRecyclerOptions<modelForCarRequest> options;
    FirebaseRecyclerAdapter<modelForCarRequest, viewHolderForRunningBId> firebaseRecyclerAdapter;
    String uid = "TEST", stauts = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__running__bid__list);

        uid = FirebaseAuth.getInstance().getUid();

        mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid).child(constants.driverBidDir); // db link
        checkRef = FirebaseDatabase.getInstance().getReference(constants.carRequestLink);
        reqRef = FirebaseDatabase.getInstance().getReference(constants.carRequestLink);


        mrecyclerview = findViewById(R.id.recyclerViewRunningBidList);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());


        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        mrecyclerview.setLayoutManager(linearLayoutManager);
        mrecyclerview.setHasFixedSize(true);

        mref.keepSynced(true);

        loadDataToFireBase();


    }

    private void loadDataToFireBase() {
        Query firebaseSearchQuery = reqRef.orderByChild("status").startAt("Bid Found").endAt("Bid Found" + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<modelForCarRequest>().setQuery(firebaseSearchQuery, modelForCarRequest.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForCarRequest, viewHolderForRunningBId>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final viewHolderForRunningBId viewholdersForCurrentTrip, final int i, @NonNull final modelForCarRequest model) {


                String postid = getItem(viewholdersForCurrentTrip.getAdapterPosition()).getPostId();


                checkRef.child(postid).child("bids").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(uid)) {


                            viewholdersForCurrentTrip.setData(getApplicationContext(),
                                    model.getPostId(), model.getUserId(), model.getUserNotificationID(), model.getDriverId(), model.getDriverNotificationID(),
                                    model.getToLoc(), model.getFromLoc(), model.getTimeDate(), model.getCarModl(), model.getDriverName(),
                                    model.getStatus(), model.getCarLicNum(), model.getFare(), model.getCarType(), model.getReqDate(), model.getTripDetails(), model.getReturnTimee(), model.getNumOfPpl(), model.getRideType());


                        }


//                        else {
//
//
//                            // viewholdersForCurrentTrip.cardView.setVisibility(View.GONE);
//
//                            viewholdersForCurrentTrip.setData(getApplicationContext(),
//                                    "TEST", "TEST", "TEST", "TEST", "TEST",
//                                    "TEST", "TEST", "TEST", "TEST", "TEST",
//                                    "TEST", "TEST", "TEST", "TEST",
//                                    "TEST", "TEST", "TEST", "TEST", "TEST");
//
//
//                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public viewHolderForRunningBId onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_trip_view_module, parent, false);
                final viewHolderForRunningBId viewholders = new viewHolderForRunningBId(iteamVIew);

                viewHolderForRunningBId.setOnClickListener(new viewHolderForRunningTrip.Clicklistener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        Intent o = new Intent(getApplicationContext(), TrpDetails.class);
                        //carry data to their
                        o.putExtra("STATUS", getItem(postion).getStatus());
                        o.putExtra("DRIVERNAME", getItem(postion).getDriverName());
                        o.putExtra("CARMODEL", getItem(postion).getCarModl());
                        o.putExtra("FORMLOC", getItem(postion).getFromLoc());
                        o.putExtra("TOLOC", getItem(postion).getToLoc());
                        o.putExtra("FARE", getItem(postion).getFare());
                        o.putExtra("TIME", getItem(postion).getTimeDate());
                        o.putExtra("POSTID", getItem(postion).getPostId());
                        o.putExtra("DRIVERUID", getItem(postion).getDriverId());
                        o.putExtra("DRIVERNOTIFICATIONID", getItem(postion).getDriverNotificationID());
                        o.putExtra("DESC", getItem(postion).getTripDetails());
                        o.putExtra("TYPE", getItem(postion).getRideType());
                        modelForCarRequest modelForCarRequest = getItem(postion);
                        o.putExtra("MODEL", modelForCarRequest);
                     //  startActivity(o);
                        //  Toast.makeText(getApplicationContext(), "fasdfas", Toast.LENGTH_SHORT).show();
                    }
                });

                return viewholders;
            }
        };
        mrecyclerview.setLayoutManager(linearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);


    }
}

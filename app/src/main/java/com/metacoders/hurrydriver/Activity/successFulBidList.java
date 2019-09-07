package com.metacoders.hurrydriver.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurrydriver.Constants.constants;
import com.metacoders.hurrydriver.Models.modelForCarRequest;
import com.metacoders.hurrydriver.R;
import com.metacoders.hurrydriver.Viewholders.viewHolderForRunningBId;

public class successFulBidList extends AppCompatActivity {

    RecyclerView mrecyclerview  ;
    LinearLayoutManager linearLayoutManager ;
    DatabaseReference mref , checkRef  , reqRef ;

    FirebaseRecyclerOptions<modelForCarRequest> options ;
    FirebaseRecyclerAdapter<modelForCarRequest, viewHolderForRunningBId> firebaseRecyclerAdapter ;
    String uid = "TEST" , stauts = null  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_ful_bid_list);


        mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid).child(constants.succfulllistDir);


        mrecyclerview = findViewById(R.id.recyclerViewUnsuccessFulBidList) ;

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());


        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        mrecyclerview.setLayoutManager(linearLayoutManager) ;
        mrecyclerview.setHasFixedSize(true);

        mref.keepSynced(true);

        loadDataToFireBase()  ;


    }
    private void loadDataToFireBase() {


        options = new FirebaseRecyclerOptions.Builder<modelForCarRequest>().setQuery(mref , modelForCarRequest.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForCarRequest, viewHolderForRunningBId>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final viewHolderForRunningBId viewholdersForCurrentTrip, final int i, @NonNull final modelForCarRequest model) {


                viewholdersForCurrentTrip.setData(getApplicationContext() ,
                        model.getPostId() , model.getUserId()  ,model.getUserNotificationID()   , model.getDriverId()  , model.getDriverNotificationID() ,
                        model.getToLoc() , model.getFromLoc() ,  model.getTimeDate() , model.getCarModl() , model.getDriverName() ,
                        model.getStatus()  , model.getCarLicNum() , model.getFare() , model.getCarType() ,
                        model.getReqDate() , model.getTripDetails() , model.getReturnTimee() , model.getNumOfPpl() ,model.getRideType() );




            }

            @NonNull
            @Override
            public viewHolderForRunningBId onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_trip_view_module, parent, false);
                final viewHolderForRunningBId viewholders = new viewHolderForRunningBId(iteamVIew);






                return viewholders;
            }
        };
        mrecyclerview.setLayoutManager(linearLayoutManager) ;
        firebaseRecyclerAdapter.startListening();
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);






    }
}

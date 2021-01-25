package com.metacoders.hurrydriver.Fragmnet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurrydriver.Activity.TrpDetails;
import com.metacoders.hurrydriver.Constants.constants;
import com.metacoders.hurrydriver.Models.driverProfileModel;
import com.metacoders.hurrydriver.Models.modelForCarRequest;
import com.metacoders.hurrydriver.R;
import com.metacoders.hurrydriver.Viewholders.viewHolderForRunningTrip;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePageFragment extends Fragment {

    View view;
    TextView nameTv, licTv, modelTv, tripNumTv, phnNumberTv, ratingTv, totalRide;
    Context context;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<modelForCarRequest> options;
    FirebaseRecyclerAdapter<modelForCarRequest, viewHolderForRunningTrip> firebaseRecyclerAdapter;
    DatabaseReference mref;
    CircleImageView profileImage;

    LinearLayoutManager linearLayoutManager;

    public HomePageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_home_page, container, false);


        // init the views
        context = view.getContext();
        nameTv = (TextView) view.findViewById(R.id.nameHomeFragment);
        licTv = (TextView) view.findViewById(R.id.licame);
        modelTv = (TextView) view.findViewById(R.id.carModelName);
        tripNumTv = (TextView) view.findViewById(R.id.completedTrioNumber);
        phnNumberTv = (TextView) view.findViewById(R.id.phoneNum);
        ratingTv = (TextView) view.findViewById(R.id.rating);
        totalRide = view.findViewById(R.id.totalRide);
        recyclerView = view.findViewById(R.id.RunningTripsList);
        linearLayoutManager = new LinearLayoutManager(context);
        //  linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        profileImage = (CircleImageView) view.findViewById(R.id.profileImage);

        // loadDataFromProfile() ;
        loadRunningProjects();

        return view;

    }

    private void loadDataFromProfile() {
        String uid = FirebaseAuth.getInstance().getUid();
        mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid);
        mref.keepSynced(true);

        Log.d("TAG", "loadDataFromProfile: " + uid);

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                driverProfileModel model = dataSnapshot.getValue(driverProfileModel.class);
                nameTv.setText(model.getDriverName());
                licTv.setText(model.getCarLic());

                if (model.getCarType().contains("Private-Car") || model.getCarType().equals("Private-Car")) {

                    modelTv.setText(model.getBuildCompany() + " " + model.getCarModel() + " " + model.getCarYear());
                } else {

                    modelTv.setText(model.getCarType());
                }

                tripNumTv.setText(model.getTotalRides());
                phnNumberTv.setText(model.getPhone());
                ratingTv.setText(model.getDriverRating());
                totalRide.setText(model.getTotalRides());
                Glide.with(context)
                        .load(model.getProfile_picture())
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(profileImage);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void loadRunningProjects() {
        String uid = FirebaseAuth.getInstance().getUid();
        mref = FirebaseDatabase.getInstance().getReference(constants.carRequestLink);
        Query query = mref.orderByChild("driverId").equalTo(uid);
        options = new FirebaseRecyclerOptions.Builder<modelForCarRequest>().setQuery(query, modelForCarRequest.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForCarRequest, viewHolderForRunningTrip>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final viewHolderForRunningTrip viewholdersForCurrentTrip, final int i, @NonNull final modelForCarRequest model) {


                viewholdersForCurrentTrip.setData(context,
                        model.getPostId(), model.getUserId(), model.getUserNotificationID(), model.getDriverId(), model.getDriverNotificationID(),
                        model.getToLoc(), model.getFromLoc(), model.getTimeDate(), model.getCarModl(), model.getDriverName(),
                        model.getStatus(), model.getCarLicNum(), model.getFare(), model.getCarType(),
                        model.getReqDate(), model.getTripDetails(), model.getReturnTimee(), model.getNumOfPpl(), model.getRideType());

                if(model.getStatus().toLowerCase().equals("completed") ||model.getStatus().toLowerCase().contains("by user") ){

                    viewholdersForCurrentTrip.itemView.setVisibility(View.GONE);
                    viewholdersForCurrentTrip.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }else {

                    viewholdersForCurrentTrip.itemView.setVisibility(View.VISIBLE);
                    viewholdersForCurrentTrip.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                }

            }

            @NonNull
            @Override
            public viewHolderForRunningTrip onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_runnning_trip, parent, false);
                final viewHolderForRunningTrip viewholders = new viewHolderForRunningTrip(iteamVIew);

                viewHolderForRunningTrip.setOnClickListener(new viewHolderForRunningTrip.Clicklistener() {
                    @Override
                    public void onItemClick(View view, final int postion) {
                        Intent o = new Intent(getContext(), TrpDetails.class);
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

                        startActivity(o);
                    }
                });

                return viewholders;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public void onStart() {

        loadDataFromProfile();
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataFromProfile();
    }
}

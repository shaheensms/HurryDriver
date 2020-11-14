package com.metacoders.hurrydriver.Fragmnet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurrydriver.Constants.constants;
import com.metacoders.hurrydriver.Models.driverProfileModel;
import com.metacoders.hurrydriver.R;

public class HomePageFragment extends Fragment {

    View view ;


        public  HomePageFragment(){


        }

        TextView nameTv , licTv , modelTv , tripNumTv , phnNumberTv  , ratingTv , totalRide;

    DatabaseReference mref ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_home_page, container, false);


        // init the views

        nameTv = (TextView) view.findViewById(R.id.nameHomeFragment);
        licTv = (TextView) view.findViewById(R.id.licame);
        modelTv = (TextView) view.findViewById(R.id.carModelName);
        tripNumTv = (TextView) view.findViewById(R.id.completedTrioNumber);
        phnNumberTv = (TextView) view.findViewById(R.id.phoneNum);
        ratingTv = (TextView) view.findViewById(R.id.rating);
        totalRide = view.findViewById(R.id.totalRide) ;



       // loadDataFromProfile() ;


        return view;

    }
    private void loadDataFromProfile() {
        String uid = FirebaseAuth.getInstance().getUid() ;
        mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid);
        mref.keepSynced(true);

        Log.d("TAG", "loadDataFromProfile: " + uid);

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                driverProfileModel  model = dataSnapshot.getValue(driverProfileModel.class);
                nameTv.setText(model.getDriverName());
                licTv.setText(model.getCarLic());

                if (model.getCarType().contains("Private-Car") ||model.getCarType().equals("Private-Car")  )
                {

                    modelTv.setText( model.getBuildCompany()+" " +model.getCarModel() + " " + model.getCarYear());
                }
                else {

                    modelTv.setText( model.getCarType());
                }

                tripNumTv.setText(model.getTotalRides());
                phnNumberTv.setText(model.getPhone());
                ratingTv.setText(model.getDriverRating());
                totalRide.setText(model.getTotalRides());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




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

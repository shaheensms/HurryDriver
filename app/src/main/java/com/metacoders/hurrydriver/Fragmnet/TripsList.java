package com.metacoders.hurrydriver.Fragmnet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurrydriver.Activity.Activity_On_UnsuccessfulBidList;
import com.metacoders.hurrydriver.Activity.Activity_Running_Bid_List;
import com.metacoders.hurrydriver.Activity.Activity_Succesful_List;
import com.metacoders.hurrydriver.Constants.constants;
import com.metacoders.hurrydriver.Models.modelForCarRequest;
import com.metacoders.hurrydriver.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TripsList extends Fragment {

    View view;
    LinearLayout runningBidCard, cancelBid, successfulBid;
    TextView f1, f2, f3;

    public static String dateFormat = "hh:mm dd-MM-yy ";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

    public TripsList() {

    }

    static String formatDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() - 15000);
        return simpleDateFormat.format(calendar.getTime());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_trip_list, container, false);

        runningBidCard = view.findViewById(R.id.runningBidCardview);
        cancelBid = view.findViewById(R.id.cancelCardview);
        successfulBid = view.findViewById(R.id.successfulBidList);
        f1 = view.findViewById(R.id.time1);
        f2 = view.findViewById(R.id.time2);
        f3 = view.findViewById(R.id.time3);

        f1.setText(formatDate());
        f2.setText(formatDate());
        f3.setText(formatDate());

        cancelBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getContext(), Activity_On_UnsuccessfulBidList.class);

                startActivity(intent);

            }
        });

        runningBidCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Activity_Running_Bid_List.class);

                startActivity(intent);

            }
        });

        successfulBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getContext(), Activity_Succesful_List.class);
                startActivity(intent);


            }
        });


        return view;
    }

//    private void countData(){
//
//
//
//        String uid = FirebaseAuth.getInstance().getUid();
//        DatabaseReferencemref = FirebaseDatabase.getInstance().getReference(constants.carRequestLink);
//
//        Query firebaseQuery = mref.orderByChild("driverId").equalTo(uid) ;
//
//        firebaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot datas: snapshot.getChildren()){
//                    modelForCarRequest carRequest  = datas.getValue(modelForCarRequest.class) ;
//                    if(carRequest.getStatus().toLowerCase().contains("completed")){
//                        total++ ;
//                    }
//                }
//
//                totalRide.setText(total+"");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
}

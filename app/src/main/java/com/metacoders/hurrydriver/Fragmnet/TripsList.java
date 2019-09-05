package com.metacoders.hurrydriver.Fragmnet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.metacoders.hurrydriver.Activity.Activity_On_UnsuccessfulBidList;
import com.metacoders.hurrydriver.Activity.Activity_Running_Bid_List;
import com.metacoders.hurrydriver.R;

public class TripsList extends Fragment {

    View view ;
    LinearLayout runningBidCard  , cancelBid;


    public  TripsList()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_trip_list, container, false);

        runningBidCard = view.findViewById(R.id.runningBidCardview) ;
        cancelBid = view.findViewById(R.id.cancelCardview) ;


        cancelBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getContext() , Activity_On_UnsuccessfulBidList.class);

                startActivity(intent);

            }
        });

        runningBidCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext() , Activity_Running_Bid_List.class);

                startActivity(intent);

            }
        });



        return  view ;
    }
}

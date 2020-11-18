package com.metacoders.hurrydriver.Viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.hurrydriver.R;


public class viewHolderForRunningBId extends RecyclerView.ViewHolder {
    public View mview;

    public TextView statusTv;
    public CardView cardView;


    public viewHolderForRunningBId(@NonNull View itemView) {

        super(itemView);

        mview = itemView;


    }

    public void setData(Context context, String postId, String userId, String userNotificationID, String driverId, String driverNotificationID,
                        String toLoc, String fromLoc, String timeDate, String carModl, String driverName,
                        String status, String carLicNum, String fare, String carType,
                        String reqDate, String tripDetails, String returntime, String numOfppl, String rideType) {


        TextView dateView = mview.findViewById(R.id.dateOfRows);
        //  TextView fareView = mview.findViewById(R.id.fareRow);


        TextView locaTo = mview.findViewById(R.id.locationTos);
        TextView locaFrom = mview.findViewById(R.id.locationFroms);
        TextView statusTv = mview.findViewById(R.id.statusRows);
        cardView = mview.findViewById(R.id.tripModule_card);


        if (postId.contains("TEST")) {
            cardView.setVisibility(View.GONE);

        } else {

            dateView.setText(timeDate);
            //    fareView.setText(fare);
            locaTo.setText(toLoc);
            locaFrom.setText(fromLoc);
            statusTv.setText(status);
        }


    }


}

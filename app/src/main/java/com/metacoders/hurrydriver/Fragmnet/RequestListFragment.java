package com.metacoders.hurrydriver.Fragmnet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurrydriver.Activity.Activity_Bid_Page_Driver;
import com.metacoders.hurrydriver.Constants.constants;
import com.metacoders.hurrydriver.Models.modelForCarRequest;
import com.metacoders.hurrydriver.R;
import com.metacoders.hurrydriver.Viewholders.viewholderForReqList;

public class RequestListFragment extends Fragment {

    RecyclerView mrecyclerview  ;
    LinearLayoutManager linearLayoutManager ;
    DatabaseReference mref;

    FirebaseRecyclerOptions<modelForCarRequest > options ;
    FirebaseRecyclerAdapter<modelForCarRequest, viewholderForReqList> firebaseRecyclerAdapter ;

    View view  ;

    public  RequestListFragment(){


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view   = inflater.inflate(R.layout.fragment_request_list, container, false);

         mref = FirebaseDatabase.getInstance().getReference(constants.carRequestLink); // db link


        mrecyclerview = view.findViewById(R.id.recyclerViewOnFramentRequestList) ;

        linearLayoutManager = new LinearLayoutManager(getContext());


        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        mrecyclerview.setLayoutManager(linearLayoutManager) ;
        mrecyclerview.setHasFixedSize(true);

        mref.keepSynced(true);

        loadDataToFireBase()  ;

        return view;

    }
    private void loadDataToFireBase() {

        options = new FirebaseRecyclerOptions.Builder<modelForCarRequest>().setQuery(mref , modelForCarRequest.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForCarRequest, viewholderForReqList>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewholderForReqList viewholdersForCurrentTrip,final int i, @NonNull modelForCarRequest model) {


                viewholdersForCurrentTrip.setDataToView(getContext() ,
                        model.getPostId() , model.getUserId()  ,model.getUserNotificationID()   , model.getDriverId()  , model.getDriverNotificationID() ,
                        model.getToLoc() , model.getFromLoc() ,  model.getTimeDate() , model.getCarModl() , model.getDriverName() ,
                        model.getStatus()  , model.getCarLicNum() , model.getFare() , model.getCarType() ,
                        model.getReqDate() , model.getTripDetails() , model.getReturnTimee() , model.getNumOfPpl() ,model.getRideType() );








            }

            @NonNull
            @Override
            public viewholderForReqList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_trip_view_module, parent, false);
                final viewholderForReqList viewholders = new viewholderForReqList(iteamVIew);



                viewholderForReqList.setOnClickListener(new viewholderForReqList.Clicklistener() {
                    @Override
                    public void onItemClick(View view, final  int postion) {

                        String DriverName  = getItem(postion).getDriverName() ;
                        String Status = getItem(postion).getStatus() ;
                        String PostID = getItem(postion).getPostId() ;
                        String userNottificaionId = getItem(postion).getUserNotificationID() ;
                        String timeOfTrip = getItem(postion).getTimeDate();
                        String fromLoc = getItem(postion).getFromLoc() ;
                        String toLoc = getItem(postion).getToLoc();
                        String numberOfppl = getItem(postion).getNumOfPpl();
                        String description = getItem(postion).getTripDetails() ;
                        String returnDate = getItem(postion).getReturnTimee() ;
                        String rideType = getItem(postion).getRideType() ;


                        //TODO you have to check for driver has already bid on this or not
                        //TODO if he didnt then   give him access




                        Intent io = new Intent(getContext() , Activity_Bid_Page_Driver.class) ;

                        io.putExtra("drivername" , DriverName) ;
                        io.putExtra("status",Status);
                        io.putExtra("postid", PostID);
                        io.putExtra("usernottificationid" , userNottificaionId);
                        io.putExtra("timeoftrip", timeOfTrip);
                        io.putExtra("fromloc", fromLoc);
                        io.putExtra("toloc", toLoc);
                        io.putExtra("numberofppl", numberOfppl);
                        io.putExtra("des", description);
                        io.putExtra("returndate", returnDate) ;
                        io.putExtra("ridetype" , rideType) ;



                        startActivity(io);









                    }
                });


                return viewholders;
            }
        };
        mrecyclerview.setLayoutManager(linearLayoutManager) ;
        firebaseRecyclerAdapter.startListening();
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);






    }
}

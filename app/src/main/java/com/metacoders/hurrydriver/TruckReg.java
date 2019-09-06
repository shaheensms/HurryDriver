package com.metacoders.hurrydriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurrydriver.Constants.constants;

import java.util.HashMap;
import java.util.Map;

public class TruckReg extends AppCompatActivity {

    AutoCompleteTextView truckTypeTv ;
    AutoCompleteTextView  truckModelTv ;
    AutoCompleteTextView  truckYearTv ;
    AutoCompleteTextView  truckTonTv ;
    Button SubmitBtn ;
    String uid = "TEST" ;



    private  static  final  String[] TRUCK_TYPE = new String[]{

            "Open" , "Closed"

    };

    private  static  final  String[] TRUCK_MODEL = new String[]{

            "Hi-Ace", "NOAH", "COROLLA", "PREMIO", "ALION"

    };

    private  static  final  String[] TRUCK_YEAR = new String[]{

            "2019", "2018", "2017", "2016"

    };

    private  static  final  String[] TRUCK_TOM = new String[]{

            "1", "2", "3", "4", "5", "6"

    };

    String truckType  ;
    String truckModel  ;
    String truckYear  ;
    String truckTon  ;
    String carType = "TRUCK"  , acType = "NULL"  ;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_reg);

        truckTypeTv = findViewById(R.id.ed_truckType);
        truckModelTv = findViewById(R.id.ed_truckModel);
        truckYearTv = findViewById(R.id.ed_truckYear);
        truckTonTv = findViewById(R.id.ed_truckTon);
        SubmitBtn  =findViewById(R.id.submitBtn);

        ArrayAdapter<String> truckTypeAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, TRUCK_TYPE) ;
        ArrayAdapter<String> truckModelAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, TRUCK_MODEL) ;
        ArrayAdapter<String>truckYearAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, TRUCK_YEAR) ;
        ArrayAdapter<String>truckTonAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, TRUCK_TOM) ;


        truckTypeTv.setAdapter(truckTypeAdapter);
        truckModelTv.setAdapter(truckModelAdapter);
        truckModelTv.setThreshold(1);
        truckYearTv.setAdapter(truckYearAdapter);
        truckTonTv.setAdapter(truckTonAdapter);




        truckTypeTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                truckTypeTv.showDropDown();
                return true;
            }
        });

        truckTonTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                truckTonTv.showDropDown();
            return   true ;
            }
        });



        //setting on click listeners

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // getting the text form  the  auto compete text view ;

                truckType = truckTypeTv.getText().toString() ;
                truckModel = truckModelTv.getText().toString() ;
                truckYear = truckYearTv.getText().toString() ;
                truckTon = truckTonTv.getText().toString() ;

                Toast.makeText(getApplicationContext() , " values : "  + truckModel+ " " + truckTon+ " " + truckYear+ " " +truckType , Toast.LENGTH_LONG)
                        .show();

                Map<String, Object> map = new HashMap<String,Object>();

                map.put("carType" ,carType) ;
                map.put("acType", acType);
                map.put("buildCompany" , truckType) ;
                map.put("carModel",truckModel);
                map.put("carYear", truckYear) ;
                map.put("truckSize",truckTon ) ;
                map.put("sitCount","null" )  ;


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid);


                databaseReference.updateChildren(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {


                            }
                        }) ;


            }
        });




    }

}

package com.metacoders.hurrydriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.AndroidException;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurrydriver.Constants.constants;

import java.util.HashMap;
import java.util.Map;

public class CarReg extends AppCompatActivity {


    AutoCompleteTextView  manufactureTv ;
    AutoCompleteTextView  carModelTv ;
    AutoCompleteTextView  carYearTv ;
    RadioGroup  acRadioGroup , carSeatNumber ;
    String acType = "NULL" , carType = "Private-Car" , carSeatNum ;
    String uid = "TEST" ;



    private  static  final  String[] COMPANY = new String[]{

      "TOYOTA", "HONDA", "TATA" , "TOYOTA" , "KIA" , "LEXUS"

    };

    private  static  final  String[] CAR_MODEL = new String[]{

            "Hi-Ace", "NOAH", "COROLLA", "PREMIO", "ALION"

    };

    private  static  final  String[] CAR_YEAR = new String[]{

            "2019", "2018", "2017", "2016"

    };




    String manufratureCompany  ;
    String carModel  ;
    String carYear  ;
    Button submitBtn ;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_reg);

        manufactureTv = findViewById(R.id.carManufacturAutoComplete);
        carModelTv = findViewById(R.id.ed_carModel);
        carYearTv = findViewById(R.id.ed_carYear);
        submitBtn = findViewById(R.id.submitBtn);
        acRadioGroup = findViewById(R.id.acTypeGroup) ;
        carSeatNumber = findViewById(R.id.numberOFPPl);


        ArrayAdapter<String>companyAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, COMPANY) ;
        ArrayAdapter<String>carModelAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, CAR_MODEL) ;
        ArrayAdapter<String>carYearAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, CAR_YEAR) ;

        manufactureTv.setAdapter(companyAdapter);
        manufactureTv.setThreshold(1);
        carModelTv.setAdapter(carModelAdapter);
        carModelTv.setThreshold(1);
        carYearTv.setAdapter(carYearAdapter);


        // getting the text form  the  auto compete text view ;

        manufactureTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                manufactureTv.showDropDown();

                return true;
            }
        });

        carModelTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                carModelTv.showDropDown();

                return true;
            }
        });


        // set ON click
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int carRadioButtonID = carSeatNumber.getCheckedRadioButtonId();
                int acRadioButtonId = acRadioGroup.getCheckedRadioButtonId() ;


                RadioButton  acRadioButton = (RadioButton) findViewById(acRadioButtonId);
                RadioButton carSeatButton = findViewById(carRadioButtonID);

                carSeatNum = carSeatButton.getText().toString();
                manufratureCompany = manufactureTv.getText().toString() ;
                carModel = carModelTv.getText().toString() ;
                carYear = carYearTv.getText().toString() ;
                acType = acRadioButton.getText().toString().toUpperCase() ;

                Map<String, Object> map = new HashMap<String,Object>();

                map.put("carType" ,carType) ;
                map.put("acType", acType);
                map.put("buildCompany" , manufratureCompany) ;
                map.put("carModel",carModel);
                map.put("carYear", carYear) ;
                map.put("truckSize","null" ) ;
                map.put("sitCount",carSeatNum)  ;

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
